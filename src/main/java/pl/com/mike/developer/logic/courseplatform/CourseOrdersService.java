package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.api.courseplatform.external.payu.OrderCancelResponse;
import pl.com.mike.developer.api.courseplatform.external.payu.PayUApi;
import pl.com.mike.developer.domain.DictionaryData;
import pl.com.mike.developer.domain.courseplatform.CourseData;
import pl.com.mike.developer.domain.courseplatform.CourseOrderData;
import pl.com.mike.developer.domain.courseplatform.CourseOrdersFilter;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.logic.DictionariesService;
import pl.com.mike.developer.logic.DictionaryType;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseOrdersService {

    private static final String SUCCESS_STATUS_CODE = "SUCCESS";
    private CourseOrdersJdbcRepository courseOrdersJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private DictionariesService dictionariesService;
    private PayUApi payUApi;

    public CourseOrdersService(CourseOrdersJdbcRepository courseOrdersJdbcRepository, CourseCustomersService courseCustomersService, DictionariesService dictionariesService, PayUApi payUApi) {
        this.courseOrdersJdbcRepository = courseOrdersJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.dictionariesService = dictionariesService;
        this.payUApi = payUApi;
    }

    public Long create(CourseOrderData data) {
        validateInvoiceData(data);
        return courseOrdersJdbcRepository.create(data);
    }

    public List<CourseOrderData> find(CourseOrdersFilter filter) {
        return courseOrdersJdbcRepository.find(filter);
    }

    public void update(CourseOrderData data) {
        validateInvoiceData(data);
        courseOrdersJdbcRepository.update(data);
    }

    public List<CourseOrderData> findOrdersForLoggedCustomer() {
        try {
            CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
            return courseOrdersJdbcRepository.find(new CourseOrdersFilter(null, loggedCustomer.getId()));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error during finding payments");
        }
    }

    public void updatePayuData(Long orderId, String payuOrderId, String payuPaymentUrl) {
        CourseOrderData order = find(new CourseOrdersFilter(orderId)).get(0);
        update(new CourseOrderData(order, payuOrderId, payuPaymentUrl));
    }

    public void processPayuNotify(Long orderId, String payUOrderStatus) {
        CourseOrderData order = find(new CourseOrdersFilter(orderId)).get(0);
        if(!order.getStatus().equals(OrderStatus.PAID.getValue())) {
            if (payUOrderStatus == null) {
                throw new IllegalArgumentException("Order status is empty!");
            } else if (payUOrderStatus.equals(PayuStatus.COMPLETED.getValue())) {
                updateStatus(orderId, OrderStatus.PAID);
                updatePayuIntegrationStatus(orderId, PayuIntegrationOrderStatus.AFTER_ORDER_COMPLETED);
            } else if (payUOrderStatus.equals(PayuStatus.CANCELED.getValue())) {
                updateStatus(orderId, OrderStatus.CANCELED);
                updatePayuIntegrationStatus(orderId, PayuIntegrationOrderStatus.AFTER_ORDER_CANCEL);
            } else if (payUOrderStatus.equals(PayuStatus.PENDING.getValue())) {
                updateStatus(orderId, OrderStatus.PENDING);
                updatePayuIntegrationStatus(orderId, PayuIntegrationOrderStatus.AFTER_ORDER_PENDING);
            } else {
                throw new IllegalArgumentException("Wrong order status!");
            }
        }

    }

    public void updateStatus(Long orderId, OrderStatus orderStatus) {
        CourseOrderData order = find(new CourseOrdersFilter(orderId)).get(0);
        update(new CourseOrderData(order, orderStatus));
    }

    public void updatePayuIntegrationStatus(Long orderId, PayuIntegrationOrderStatus payuIntegrationOrderStatus) {
        CourseOrderData order = find(new CourseOrdersFilter(orderId)).get(0);
        update(new CourseOrderData(order, payuIntegrationOrderStatus));
    }

    public void cancelOrderInPayu(Long orderId) {
        CourseOrderData order = find(new CourseOrdersFilter(orderId)).get(0);

        if(order.getStatus().equals(OrderStatus.PAID.getValue())) {
            throw new IllegalArgumentException("You can't cancel this order, because is paid");
        } else if (order.getStatus().equals(OrderStatus.CANCELED.getValue())) {
            throw new IllegalArgumentException("You can't cancel this order, because is already canceled");
        }

        OrderCancelResponse orderCancelResponse = payUApi.cancelOrder(order);

        if(!orderCancelResponse.getStatus().getStatusCode().equals(SUCCESS_STATUS_CODE)) {
            throw new IllegalArgumentException("Cancellation not completed successfully. Description: " + orderCancelResponse.getStatus().getStatusDesc());
        }
    }

    public CourseOrderData getOrderMadeByCustomerWithCourse(CustomerData customer, CourseData course) {
        return courseOrdersJdbcRepository.getOrderMadeByCustomerWithCourse(customer, course);
    }

    private void validateInvoiceData(CourseOrderData data) {

        if(data.getTotalPrice().compareTo(BigDecimal.ZERO) != 0) {
            if (data.getInvoiceType() == null) {
                throw new IllegalArgumentException("Invoice type can't be empty");
            } else if (data.getInvoiceType().equals(BillingType.COMPANY.getCode())) {
                validateInvoiceField(data.getInvoiceCompanyName(), "Company name");
                validateInvoiceField(data.getInvoiceNip(), "NIP");
            } else if (data.getInvoiceType().equals(BillingType.PRIVATE_PERSON.getCode())) {
                validateInvoiceField(data.getInvoiceFirstAndLastName(), "First and last name");
            } else {
                throw new IllegalArgumentException("Incorrect invoice type");
            }

            validateInvoiceField(data.getInvoiceStreet(), "Street");
            validateInvoiceField(data.getInvoicePostalCode(), "Postal code");
            validateInvoiceField(data.getInvoiceCity(), "City");
            validateInvoiceCountry(data.getInvoiceCountry());
        }
    }

    private void validateInvoiceField(String invoiceField, String fieldName) {
        if(invoiceField == null || invoiceField.equals("")) {
            throw new IllegalArgumentException(fieldName + " field can't be empty");
        } else if(invoiceField.length() > 300) {
            throw new IllegalArgumentException(fieldName + " field too long, max 300 characters");
        }
    }

    private void validateInvoiceCountry(String invoiceCountry) {
        if(invoiceCountry == null || invoiceCountry.equals("")) {
            throw new IllegalArgumentException("Country can't be empty");
        } else if (!isCountryInDictionary(invoiceCountry)) {
            throw new IllegalArgumentException("Incorrect country");
        }

    }

    private Boolean isCountryInDictionary(String invoiceCountry) {
        List<DictionaryData> countries = dictionariesService.getDictionary(DictionaryType.COUNTRIES);

        for (DictionaryData country : countries) {
            if(invoiceCountry.equals(country.getCode())) {
                return true;
            }
        }

        return false;
    }

}
