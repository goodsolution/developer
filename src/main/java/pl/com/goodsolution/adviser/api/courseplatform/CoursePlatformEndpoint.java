package pl.com.goodsolution.adviser.api.courseplatform;

import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.domain.InvoiceData;
import pl.com.goodsolution.adviser.domain.courseplatform.BasketData;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseData;
import pl.com.goodsolution.adviser.domain.courseplatform.CoursesFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.itube.ITubeFilter;
import pl.com.goodsolution.adviser.domain.itube.ITubesGetResponse;
import pl.com.goodsolution.adviser.logic.courseplatform.BasketService;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseCustomersService;
import pl.com.goodsolution.adviser.logic.courseplatform.CoursesService;
import pl.com.goodsolution.adviser.domain.courseplatform.*;
import pl.com.goodsolution.adviser.logic.courseplatform.*;
import pl.com.goodsolution.adviser.logic.courseplatform.notifications.NotificationType;
import pl.com.goodsolution.adviser.logic.courseplatform.notifications.NotificationsService;
import pl.com.goodsolution.adviser.logic.itube.ITubeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pl.com.goodsolution.adviser.logic.courseplatform.ConverterToResponsesUtil.*;

@RestController
@RequestMapping("/api/crs")
public class CoursePlatformEndpoint {

    private CourseCustomersService courseCustomersService;
    private CoursesService coursesService;
    private BasketService basketService;
    private CourseOrdersService courseOrdersService;
    private PurchasedCoursesService purchasedCoursesService;
    private LessonsService lessonsService;
    private InvoicesService invoicesService;
    private PasswordResetTokensService passwordResetTokensService;
    private MemesService memesService;
    private CourseCommentsService courseCommentsService;
    private TraceService traceService;
    private LessonCommentsService lessonCommentsService;
    private CourseAttachmentsService courseAttachmentsService;
    private CustomerLessonDetailsService customerLessonDetailsService;
    private ModulesService modulesService;
    private LessonAttachmentsService lessonAttachmentsService;
    private EmailConfirmationService emailConfirmationService;
    private NotificationsService notificationsService;
    private JobOffersService jobOffersService;


    public CoursePlatformEndpoint(CourseCustomersService courseCustomersService, CoursesService coursesService, BasketService basketService, CourseOrdersService courseOrdersService, PurchasedCoursesService purchasedCoursesService, LessonsService lessonsService, InvoicesService invoicesService, PasswordResetTokensService passwordResetTokensService, MemesService memesService, CourseCommentsService courseCommentsService, TraceService traceService, LessonCommentsService lessonCommentsService, CourseAttachmentsService courseAttachmentsService, CustomerLessonDetailsService customerLessonDetailsService, ModulesService modulesService, LessonAttachmentsService lessonAttachmentsService, EmailConfirmationService emailConfirmationService, NotificationsService notificationsService, JobOffersService jobOffersService) {
        this.courseCustomersService = courseCustomersService;
        this.coursesService = coursesService;
        this.basketService = basketService;
        this.courseOrdersService = courseOrdersService;
        this.purchasedCoursesService = purchasedCoursesService;
        this.lessonsService = lessonsService;
        this.invoicesService = invoicesService;
        this.passwordResetTokensService = passwordResetTokensService;
        this.memesService = memesService;
        this.courseCommentsService = courseCommentsService;
        this.traceService = traceService;
        this.lessonCommentsService = lessonCommentsService;
        this.courseAttachmentsService = courseAttachmentsService;
        this.customerLessonDetailsService = customerLessonDetailsService;
        this.modulesService = modulesService;
        this.lessonAttachmentsService = lessonAttachmentsService;
        this.emailConfirmationService = emailConfirmationService;
        this.notificationsService = notificationsService;
        this.jobOffersService = jobOffersService;
    }

    //TODO Merge register-xxx into one
    @PostMapping("/register-customer")
    public void register(@RequestBody CustomerPostRequest request, HttpServletRequest httpServletRequest) {
        CustomerData customerCreateRequest = new CustomerData(request.getLogin(), request.getPasswordHash(), request.getLanguage(), request.getRegulationAccepted(), request.getNewsletterAccepted(), request.getIsEnabled(), request.getIsEmailConfirmed(), httpServletRequest, new String[]{CustomerAuthority.USER.getCode()});
        courseCustomersService.registerCustomer(customerCreateRequest);
    }

    @PostMapping("/register-teacher")
    public void registerTeacher(@RequestBody CustomerPostRequest request, HttpServletRequest httpServletRequest) {
        CustomerData customerCreateRequest = new CustomerData(request.getLogin(), request.getPasswordHash(), request.getLanguage(),
                request.getRegulationAccepted(), request.getNewsletterAccepted(), request.getAuthorFirstName(), request.getAuthorLastName(), httpServletRequest, new String[]{CustomerAuthority.TEACHER.getCode()});
        courseCustomersService.registerTeacher(customerCreateRequest);
    }

    @PostMapping("/register-student")
    public void registerStudent(@RequestBody CustomerPostRequest request, HttpServletRequest httpServletRequest) {
        CustomerData customerCreateRequest = new CustomerData(request.getLogin(), request.getPasswordHash(), request.getLanguage(), request.getRegulationAccepted(), request.getNewsletterAccepted(), request.getIsEnabled(), request.getIsEmailConfirmed(), httpServletRequest, new String[]{CustomerAuthority.STUDENT.getCode()});
        courseCustomersService.registerCustomer(customerCreateRequest);
    }

    @GetMapping("/courses")
    public List<CourseGetResponse> courses(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "type") String type) {
        List<CourseData> courses = coursesService.find(new CoursesFilter(title, false, CourseVisibilityStatus.VISIBLE, type));
        return coursesToResponses(courses);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        courseCustomersService.changeLoggedUsersPassword(request.getActualPasswordHash(), request.getNewPasswordHash());
    }

    @GetMapping("/courses-bought-by-logged-customer")
    public List<CourseGetResponse> coursesBoughtByLoggedCustomer(@RequestParam String courseType) {
        List<CourseData> courses = coursesService.findCoursesOwnedByLoggedCustomer(courseType);
        return coursesToResponses(courses);
    }

    @PostMapping("/add-course-to-basket/{id}")
    public void addCourseToBasket(@PathVariable Long id, HttpServletRequest request) {
        basketService.addCourseToBasket(id, request);
    }

    @GetMapping("/courses-in-basket")
    public BasketGetResponse coursesInBasket(HttpSession session) {
        BasketData basket = basketService.getBasket(session);
        return new BasketGetResponse(coursesToResponses(basket.getCourses()), basket.getTotalPrice());
    }

    @DeleteMapping("/delete-course-from-basket/{id}")
    public void deleteCourseFromBasket(@PathVariable Long id, HttpServletRequest request) {
        basketService.deleteCourseFromBasket(id, request);
    }

    @PostMapping("/order-courses-in-basket")
    public String orderCoursesInBasket(HttpSession session, HttpServletRequest servletRequest, @RequestBody OrderCoursesInBasketPostRequest request) {
        return basketService.orderCoursesInBasket(session, servletRequest, new Billing(request));
    }

    @GetMapping("/basket/courses/quantity")
    public BasketCoursesQuantityGetResponse getCoursesInBasketQuantity(HttpSession session) {
        return new BasketCoursesQuantityGetResponse(basketService.getCoursesInBasketQuantity(session));
    }

    @GetMapping("/orders/logged-customer")
    public List<CourseOrderGetResponse> loggedCustomersOrders() {
        return ordersToResponses(courseOrdersService.findOrdersForLoggedCustomer());
    }

    @GetMapping("/order-details/{orderId}")
    public OrderDetailsGetResponse purchasedCourses(@PathVariable Long orderId) {

        CourseOrderData order = courseOrdersService.find(new CourseOrdersFilter(orderId)).get(0);
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if (loggedCustomer == null) {
            throw new IllegalArgumentException("Your are logged out. Please log in and retry");
        }

        if (!order.getCustomer().getId().equals(loggedCustomer.getId())) {
            throw new IllegalArgumentException("You don't have permissions to this endpoint");
        }

        List<PurchasedCourseGetResponse> purchasedCourses = purchasedCoursesToResponses(purchasedCoursesService.find(new PurchasedCoursesFilter(orderId)));
        List<InvoiceGetResponse> invoices = invoicesToResponses(invoicesService.find(new InvoicesFilter(null, orderId, false)));

        return new OrderDetailsGetResponse(purchasedCourses, invoices, new CourseOrderGetResponse(order));
    }

    @PutMapping("/logged-customer")
    public void updateLoggedCustomer(@RequestBody LoggedCustomerPutRequest request) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if (loggedCustomer == null) {
            throw new IllegalArgumentException("Your are logged out. Please log in and retry");
        }

        courseCustomersService.updateLoggedCustomer(request.getLanguage(), request.getNewsletterAccepted());
    }

    @GetMapping("/lesson/{id}")
    public LessonRequestGetResponse getLesson(@PathVariable Long id) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if (loggedCustomer == null) {
            throw new IllegalArgumentException("Your are logged out. Please log in and retry");
        }

        LessonData lesson = lessonsService.getWithCustomerActivity(id, loggedCustomer);

        if (!hasUserCourse(lesson.getCourse().getId(), loggedCustomer)) {
            throw new IllegalArgumentException("Access denied");
        }

        return new LessonRequestGetResponse(new LessonGetResponse(lesson));
    }

    @GetMapping("/lessons/{courseId}")
    public LessonsRequestGetResponse getLessons(@PathVariable Long courseId) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if (loggedCustomer == null) {
            throw new IllegalArgumentException("Your are logged out. Please log in and retry");
        }

        if (!hasUserCourse(courseId, loggedCustomer)) {
            throw new IllegalArgumentException("Access denied");
        }

        List<LessonData> lessonsWithoutModule = lessonsService.findWithCustomerActivity(courseId, loggedCustomer, true, null);
        List<ModuleData> modulesInCourse = modulesService.find(new ModulesFilter(courseId, false, ModuleVisibilityStatus.VISIBLE));

        List<ModuleGetResponse> modulesInCourseResponses = new ArrayList<>();

        for (ModuleData moduleInCourse : modulesInCourse) {
            List<LessonData> lessons = lessonsService.findWithCustomerActivity(courseId, loggedCustomer, null, moduleInCourse.getId());
            modulesInCourseResponses.add(new ModuleGetResponse(moduleInCourse, lessonsToResponses(lessons)));
        }

        return new LessonsRequestGetResponse(lessonsToResponses(lessonsWithoutModule), modulesInCourseResponses);
    }

    @PutMapping("/lesson/{id}/change-watched-status")
    public void markLessonAsWatched(@PathVariable Long id) {
        customerLessonDetailsService.changeLessonWatchedStatus(id);
    }

    @PostMapping("/customer/forget-password")
    public void forgetPassword(@RequestParam String email) {
        passwordResetTokensService.prepareAndSendToken(email);
    }

    @PutMapping("/customer/reset-password")
    public void resetPassword(@RequestBody ResetPasswordPutRequest request) {
        passwordResetTokensService.resetPassword(request.getToken(), request.getPasswordHash());
    }

    @GetMapping("/memes/{page}")
    public MemesRequestGetResponse getMemes(@PathVariable Long page) {
        return new MemesRequestGetResponse(memesToResponses(memesService.findMemesForPage(page)));
    }

    @PostMapping("/course-comment")
    public void addCourseComment(@RequestBody CourseCommentPostRequest request) {
        courseCommentsService.createByLoggedCustomer(request.getText(), request.getCourseId());
    }

    @GetMapping("/course-comments/{courseId}")
    public CourseCommentsRequestGetResponsePublic getCourseComments(@PathVariable Long courseId) {
        List<CourseCommentData> courseComments = courseCommentsService.findForCoursePage(courseId);
        return new CourseCommentsRequestGetResponsePublic(courseCommentsToResponsesPublic(courseComments));
    }

    @PostMapping("/lesson-comment")
    public void createLessonComment(@RequestBody LessonCommentPostRequest request) {
        lessonCommentsService.create(request.getText(), request.getLessonId());
    }

    @GetMapping("/lesson-comments/{lessonId}")
    public LessonCommentsRequestGetResponsePublic findLessonComments(@PathVariable Long lessonId) {
        List<LessonCommentData> lessonComments = lessonCommentsService.findForLessonWatchPage(lessonId);
        return new LessonCommentsRequestGetResponsePublic(lessonCommentsToResponsesPublic(lessonComments));
    }

    @PostMapping("/trace")
    public void trace(@RequestBody TracePostRequest request, HttpServletRequest httpServletRequest) {
        traceService.trace(new TraceData(request.getWhat(), request.getValue(), request.getWho(), WebUtil.getClientIp(httpServletRequest), request.getBrowser(), request.getOperatingSystem()));
    }

    @GetMapping("/course/{id}/attachments")
    public CourseAttachmentsRequestGetResponse getCourseAttachments(@PathVariable(value = "id") Long courseId) {
        List<CourseAttachmentData> courseAttachments = courseAttachmentsService.findForCourse(courseId);
        return new CourseAttachmentsRequestGetResponse(courseAttachmentsToResponses(courseAttachments));
    }

    @GetMapping("/lesson/{id}/attachments")
    public LessonAttachmentsGetResponse getLessonAttachments(@PathVariable(value = "id") Long lessonId) {
        List<LessonAttachmentData> lessonAttachments = lessonAttachmentsService.find(new LessonAttachmentsFilter(lessonId, false));

        List<LessonAttachmentGetResponse> responses = new ArrayList<>();

        for (LessonAttachmentData lessonAttachment : lessonAttachments) {
            responses.add(new LessonAttachmentGetResponse(lessonAttachment.getId(), lessonAttachment.getName(), lessonAttachment.getFile().getOriginalName()));
        }

        return new LessonAttachmentsGetResponse(responses);
    }

    @PostMapping("/send/email-confirmation-link")
    public void sendConfirmationEmail() {
        emailConfirmationService.sendEmailConfirmationLink();
    }

    @GetMapping("/notifications/count/unseen")
    public UnseenNotificationsCountRequestGetResponse getUnseenNotifications() {

        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("You must be logged to get your unseen notifications count");
        }

        return new UnseenNotificationsCountRequestGetResponse(notificationsService.unseenNotificationsCount(customer));
    }

    @GetMapping("/notifications")
    public NotificationsRequestGetResponse getNotifications() {

        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("You must be logged to get your notifications");
        }

        List<NotificationData> notifications = notificationsService.find(new NotificationsFilter(false, customer, NotificationType.PLATFORM, 10L));
        notificationsService.setNotificationsSeen(notifications);
        return new NotificationsRequestGetResponse(notificationsToResponses(notifications));
    }

    @GetMapping("/job-offers/{page}")
    public JobOffersGetResponse findJobOffers(@PathVariable Long page) {
        return new JobOffersGetResponse(jobOffersToResponses(jobOffersService.find(new JobOffersFilter(page, 10L, Boolean.FALSE))));
    }

    private List<NotificationGetResponse> notificationsToResponses(List<NotificationData> notifications) {

        List<NotificationGetResponse> responses = new ArrayList<>();

        for (NotificationData notification : notifications) {

            LocalDateTime dateTime = notification.getCreateDatetime();

            String createDatetime = dateTime.getHour() + ":" + dateTime.getMinute() + " " + dateTime.getDayOfMonth() + "." + dateTime.getMonthValue() + "." + dateTime.getYear();
            String title = notification.getTitle();
            String content = notification.getContent();
            String link = notification.getLink();
            boolean seen = notification.getSeenDatetime() == null;

            responses.add(new NotificationGetResponse(createDatetime, title, content, link, seen));
        }

        return responses;
    }

    private List<InvoiceGetResponse> invoicesToResponses(List<InvoiceData> invoices) {
        List<InvoiceGetResponse> list = new ArrayList<>();
        for (InvoiceData invoice : invoices) {
            list.add(new InvoiceGetResponse(invoice));
        }
        return list;
    }

    private List<CourseGetResponse> coursesToResponses(List<CourseData> courses) {
        List<CourseGetResponse> list = new ArrayList<>();
        for (CourseData course : courses) {
            list.add(new CourseGetResponse(course));
        }
        return list;
    }

    private List<CourseOrderGetResponse> ordersToResponses(List<CourseOrderData> orders) {
        List<CourseOrderGetResponse> responses = new ArrayList<>();
        for (CourseOrderData order : orders) {
            responses.add(new CourseOrderGetResponse(order));
        }
        return responses;
    }

    private List<PurchasedCourseGetResponse> purchasedCoursesToResponses(List<PurchasedCourseData> purchasedCourses) {
        List<PurchasedCourseGetResponse> responses = new ArrayList<>();
        for (PurchasedCourseData purchasedCourse : purchasedCourses) {
            responses.add(new PurchasedCourseGetResponse(purchasedCourse));
        }
        return responses;
    }

    private Boolean hasUserCourse(Long courseId, CustomerData customer) {
        List<CourseData> ownedCourses = coursesService.findCoursesOwnedByCustomer(customer);
        for (CourseData ownedCourse : ownedCourses) {
            if (ownedCourse.getId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

}
