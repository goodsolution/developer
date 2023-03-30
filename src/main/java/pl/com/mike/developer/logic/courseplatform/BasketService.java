package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.api.courseplatform.external.payu.PayUApi;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class BasketService {

    private static final String BASKET_SESSION_ATTRIBUTE_NAME = "BASKET";
    private CoursesService coursesService;
    private CourseCustomersService courseCustomersService;
    private CourseOrdersService courseOrdersService;
    private PurchasedCoursesService purchasedCoursesService;
    private PayUApi payUApi;
    private EmailService emailService;
    private ApplicationConfig applicationConfig;

    public BasketService(CoursesService coursesService, CourseCustomersService courseCustomersService, CourseOrdersService courseOrdersService, PurchasedCoursesService purchasedCoursesService, PayUApi payUApi, EmailService emailService, ApplicationConfig applicationConfig) {
        this.coursesService = coursesService;
        this.courseCustomersService = courseCustomersService;
        this.courseOrdersService = courseOrdersService;
        this.purchasedCoursesService = purchasedCoursesService;
        this.payUApi = payUApi;
        this.emailService = emailService;
        this.applicationConfig = applicationConfig;
    }

    public String orderCoursesInBasket(HttpSession session, HttpServletRequest servletRequest, Billing billing) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        validateLoggedCustomer(loggedCustomer);

        BasketData basket = getBasket(session);
        validateBasket(basket);

        Long orderId;
        String redirectUrl;

        if(basket.getTotalPrice().compareTo(BigDecimal.ZERO) == 0) {
            orderId = courseOrdersService.create(new CourseOrderData(UUID.randomUUID().toString(), loggedCustomer, LocalDateTime.now(),
                    basket.getTotalPrice(), OrderStatus.PAID.getValue(), new Billing(), null));

            purchasedCoursesService.create(basket.getCourses(), courseOrdersService.find(new CourseOrdersFilter(orderId, null)).get(0));

            redirectUrl = applicationConfig.getCoursePlatformUrl() + "/thanks-for-shopping";
        } else {
            orderId = courseOrdersService.create(new CourseOrderData(UUID.randomUUID().toString(), loggedCustomer, LocalDateTime.now(),
                    basket.getTotalPrice(), OrderStatus.UNPAID.getValue(), billing, PayuIntegrationOrderStatus.BEFORE_AUTH.getStatus()));

            purchasedCoursesService.create(basket.getCourses(), courseOrdersService.find(new CourseOrdersFilter(orderId, null)).get(0));

            PayUAuthorizationData authorizationData = payUApi.getAuthorization();
            courseOrdersService.updatePayuIntegrationStatus(orderId, PayuIntegrationOrderStatus.BEFORE_CREATE_ORDER);

            PayuCreateOrderResponseData createOrderResponse = payUApi.createOrder(authorizationData.getAccessToken(),loggedCustomer, basket, orderId, servletRequest.getRemoteAddr());
            courseOrdersService.updatePayuIntegrationStatus(orderId, PayuIntegrationOrderStatus.AFTER_CREATE_ORDER);

            String paymentUrl = createOrderResponse.getRedirectUri();
            String payuOrderId = createOrderResponse.getOrderId();
            courseOrdersService.updatePayuData(orderId, payuOrderId, paymentUrl);

            courseCustomersService.update(new CustomerData(loggedCustomer, billing));

            redirectUrl = paymentUrl;
        }

        clearBasket(session);

        emailService.sendAfterOrderMail(loggedCustomer, Locale.forLanguageTag(loggedCustomer.getLanguage()),
                courseOrdersService.find(new CourseOrdersFilter(orderId)).get(0), coursesService.findCoursesInOrder(orderId));

        return redirectUrl;
    }

    public void addCourseToBasket(Long id, HttpServletRequest request) {

        @SuppressWarnings("unchecked")
        BasketData basket = (BasketData) request.getSession().getAttribute(BASKET_SESSION_ATTRIBUTE_NAME);

        if (basket == null) {
            basket = prepareEmptyBasket();
            request.getSession().setAttribute(BASKET_SESSION_ATTRIBUTE_NAME, basket);
        }

        CourseData courseToAdd = coursesService.find(new CoursesFilter(id)).get(0);

        if(courseToAdd.getSaleStatus().equals(CourseSaleStatus.CLOSED.getCode())) {
            throw new IllegalArgumentException("You can't add this course to basket - sale is closed");
        }

        if(courseCustomersService.getLoggedCustomer() != null) {
            checkIfCustomerHaveThisCourse(courseToAdd);
            checkIfCustomerOrderedCourseBefore(courseToAdd);
        }

        checkIfCustomerHaveThisCourseInBasket(courseToAdd, basket);

        List<CourseData> coursesInBasket = basket.getCourses();

        if(!existCourseInBasket(courseToAdd, coursesInBasket)) {
            coursesInBasket.add(courseToAdd);
            request.getSession().setAttribute(BASKET_SESSION_ATTRIBUTE_NAME, new BasketData(coursesInBasket, countTotalPrice(coursesInBasket)));
        }

    }

    public Long getCoursesInBasketQuantity(HttpSession session) {
        return (long) getBasket(session).getCourses().size();
    }

    public BasketData getBasket(HttpSession session) {

        @SuppressWarnings("unchecked")
        BasketData basket = (BasketData) session.getAttribute(BASKET_SESSION_ATTRIBUTE_NAME);

        if (basket == null) {
            basket = prepareEmptyBasket();
        }

        return basket;

    }

    public void deleteCourseFromBasket(Long id, HttpServletRequest request) {

        @SuppressWarnings("unchecked")
        BasketData basket = (BasketData) request.getSession().getAttribute(BASKET_SESSION_ATTRIBUTE_NAME);

        List<CourseData> coursesInBasket = basket.getCourses();

        coursesInBasket.removeIf(courseInBasket -> id.equals(courseInBasket.getId()));

        request.getSession().setAttribute(BASKET_SESSION_ATTRIBUTE_NAME, new BasketData(coursesInBasket, countTotalPrice(coursesInBasket)));

    }

    private void clearBasket(HttpSession session) {
        session.setAttribute(BASKET_SESSION_ATTRIBUTE_NAME, prepareEmptyBasket());
    }

    private Boolean existCourseInBasket(CourseData courseToAdd, List<CourseData> coursesInBasket) {

        for (CourseData courseInBasket : coursesInBasket) {
            if(courseToAdd.getId().equals(courseInBasket.getId())) {
                return true;
            }
        }

        return false;
    }

    private BigDecimal countTotalPrice(List<CourseData> coursesInBasket) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CourseData courseInBasket : coursesInBasket) {
            totalPrice = totalPrice.add(courseInBasket.getPrice());
        }
        return totalPrice;
    }

    private BasketData prepareEmptyBasket() {
        return new BasketData(new ArrayList<>(), new BigDecimal(0));
    }

    private void checkIfCustomerHaveThisCourse(CourseData course) {
        List<CourseData> coursesBoughtByUser = coursesService.findCoursesOwnedByLoggedCustomer();
        for (CourseData courseBoughtByUser : coursesBoughtByUser) {
            if(course.getId().equals(courseBoughtByUser.getId())) {
                throw new IllegalArgumentException("You can't add this product to basket because you have this product");
            }
        }
    }

    private void checkIfCustomerOrderedCourseBefore(CourseData courseToAdd) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        List<CourseData> coursesInUnpaidOrders = coursesService.findCoursesOrderedByCustomer(loggedCustomer, OrderStatus.UNPAID);
        List<CourseData> coursesInPendingOrders = coursesService.findCoursesOrderedByCustomer(loggedCustomer, OrderStatus.PENDING);

        for (CourseData courseInUnpaidOrders : coursesInUnpaidOrders) {
            if(courseToAdd.getId().equals(courseInUnpaidOrders.getId())) {
                throw new IllegalArgumentException("You can't add this product to basket, because you have this product in unpaid order. Check your payments");
            }
        }

        for (CourseData courseInPendingOrders : coursesInPendingOrders) {
            if(courseToAdd.getId().equals(courseInPendingOrders.getId())) {
                throw new IllegalArgumentException("You can't add this product to basket, because you have this product in pending order. Check your payments");
            }
        }
    }

    private void checkIfCustomerHaveThisCourseInBasket(CourseData courseToAdd, BasketData basket) {
        List<CourseData> coursesInBasket = basket.getCourses();
        for (CourseData courseInBasket : coursesInBasket) {
            if(courseToAdd.getId().equals(courseInBasket.getId())) {
                throw new IllegalArgumentException("You can't add this product to basket, because you have this product in basket!");
            }
        }
    }

    private void validateBasket(BasketData basket) {

        List<CourseData> coursesInBasket = basket.getCourses();
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        List<CourseData> coursesBoughtByLoggedCustomer = coursesService.findCoursesOwnedByLoggedCustomer();
        List<CourseData> coursesInUnpaidOrders = coursesService.findCoursesOrderedByCustomer(loggedCustomer, OrderStatus.UNPAID);
        List<CourseData> coursesInPendingOrders = coursesService.findCoursesOrderedByCustomer(loggedCustomer, OrderStatus.PENDING);

        for (CourseData courseInBasket : coursesInBasket) {
            for (CourseData courseBoughtByLoggedCustomer : coursesBoughtByLoggedCustomer) {
                if(courseInBasket.getId().equals(courseBoughtByLoggedCustomer.getId())) {
                    throw new IllegalArgumentException("Remove this product from basket: " + courseInBasket.getTitle() + ", you have this product");
                }
            }

            for (CourseData courseInUnpaidOrders : coursesInUnpaidOrders) {
                if(courseInBasket.getId().equals(courseInUnpaidOrders.getId())) {
                    throw new IllegalArgumentException("Remove this product from basket: " + courseInBasket.getTitle() + ", you have this product in unpaid order");
                }
            }

            for (CourseData courseInPendingOrders : coursesInPendingOrders) {
                if(courseInBasket.getId().equals(courseInPendingOrders.getId())) {
                    throw new IllegalArgumentException("Remove this product from basket: " + courseInBasket.getTitle() + ", you have this product in pending order");
                }
            }
        }

    }

    private void validateLoggedCustomer(CustomerData loggedCustomer) {
        if(loggedCustomer == null) {
            throw new IllegalArgumentException("You must be logged to complete order!");
        }
    }

}
