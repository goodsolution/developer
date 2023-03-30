package pl.com.mike.developer.api;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderPutRequest {
    private Long orderStatusId;
    private String email;
    private Boolean invoiceIssuedStatus;
    private Boolean receipt;
    private Boolean demandingCustomer;
    private Long paymentMethodId;
    private Boolean paymentByCard;
    private Long paymentStatusId;
    private Long deliveryMethodId;
    private Boolean groupOrders;

    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerStreet;
    private String customerBuildingNumber;
    private String customerApartmentNumber;
    private String customerPostalCode;
    private Long customerCityId;

    private LocalTime hoursTo;
    private Boolean weekendHours;
    private LocalTime weekendHoursTo;

    private Boolean weekendAddress;
    private String weekendStreet;
    private String weekendBuildingNumber;
    private String weekendApartmentNumber;
    private String weekendPostalCode;
    private Long weekendCityId;

    private Boolean invoiceWanted;
    private String invoiceCompanyName;
    private String invoiceNip;
    private String invoiceStreet;
    private String invoiceBuildingNumber;
    private String invoiceApartmentNumber;
    private String invoicePostalCode;
    private String invoiceCityName;

    private String comments;
    private String adminComments;
    private Boolean driverExclude;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate stopDate;

    private String cancelReason;


    public OrderPutRequest() {
    }

    public OrderPutRequest(Long orderStatusId, String email, Boolean invoiceIssuedStatus, Boolean receipt, Boolean demandingCustomer, Long paymentMethodId, Boolean paymentByCard, Long paymentStatusId, Long deliveryMethodId, Boolean groupOrders, String customerFirstName, String customerLastName, String customerPhone, String customerStreet, String customerBuildingNumber, String customerApartmentNumber, String customerPostalCode, Long customerCityId, LocalTime hoursTo, Boolean weekendHours, LocalTime weekendHoursTo, Boolean weekendAddress, String weekendStreet, String weekendBuildingNumber, String weekendApartmentNumber, String weekendPostalCode, Long weekendCityId, Boolean invoiceWanted, String invoiceCompanyName, String invoiceNip, String invoiceStreet, String invoiceBuildingNumber, String invoiceApartmentNumber, String invoicePostalCode, String invoiceCityName, String comments, String adminComments, Boolean driverExclude, LocalDate startDate, LocalDate stopDate, String cancelReason) {
        this.orderStatusId = orderStatusId;
        this.email = email;
        this.invoiceIssuedStatus = invoiceIssuedStatus;
        this.receipt = receipt;
        this.demandingCustomer = demandingCustomer;
        this.paymentMethodId = paymentMethodId;
        this.paymentByCard = paymentByCard;
        this.paymentStatusId = paymentStatusId;
        this.deliveryMethodId = deliveryMethodId;
        this.groupOrders = groupOrders;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerStreet = customerStreet;
        this.customerBuildingNumber = customerBuildingNumber;
        this.customerApartmentNumber = customerApartmentNumber;
        this.customerPostalCode = customerPostalCode;
        this.customerCityId = customerCityId;
        this.hoursTo = hoursTo;
        this.weekendHours = weekendHours;
        this.weekendHoursTo = weekendHoursTo;
        this.weekendAddress = weekendAddress;
        this.weekendStreet = weekendStreet;
        this.weekendBuildingNumber = weekendBuildingNumber;
        this.weekendApartmentNumber = weekendApartmentNumber;
        this.weekendPostalCode = weekendPostalCode;
        this.weekendCityId = weekendCityId;
        this.invoiceWanted = invoiceWanted;
        this.invoiceCompanyName = invoiceCompanyName;
        this.invoiceNip = invoiceNip;
        this.invoiceStreet = invoiceStreet;
        this.invoiceBuildingNumber = invoiceBuildingNumber;
        this.invoiceApartmentNumber = invoiceApartmentNumber;
        this.invoicePostalCode = invoicePostalCode;
        this.invoiceCityName = invoiceCityName;
        this.comments = comments;
        this.adminComments = adminComments;
        this.driverExclude = driverExclude;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.cancelReason = cancelReason;
    }


    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getInvoiceIssuedStatus() {
        return invoiceIssuedStatus;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Boolean getPaymentByCard() {
        return paymentByCard;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public Boolean getGroupOrders() {
        return groupOrders;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerStreet() {
        return customerStreet;
    }

    public String getCustomerBuildingNumber() {
        return customerBuildingNumber;
    }

    public String getCustomerApartmentNumber() {
        return customerApartmentNumber;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public Long getCustomerCityId() {
        return customerCityId;
    }

    public LocalTime getHoursTo() {
        return hoursTo;
    }

    public Boolean getWeekendHours() {
        return weekendHours;
    }

    public LocalTime getWeekendHoursTo() {
        return weekendHoursTo;
    }

    public Boolean getWeekendAddress() {
        return weekendAddress;
    }

    public String getWeekendStreet() {
        return weekendStreet;
    }

    public String getWeekendBuildingNumber() {
        return weekendBuildingNumber;
    }

    public String getWeekendApartmentNumber() {
        return weekendApartmentNumber;
    }

    public String getWeekendPostalCode() {
        return weekendPostalCode;
    }

    public Long getWeekendCityId() {
        return weekendCityId;
    }

    public Boolean getInvoiceWanted() {
        return invoiceWanted;
    }

    public String getInvoiceCompanyName() {
        return invoiceCompanyName;
    }

    public String getInvoiceNip() {
        return invoiceNip;
    }

    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    public String getInvoiceBuildingNumber() {
        return invoiceBuildingNumber;
    }

    public String getInvoiceApartmentNumber() {
        return invoiceApartmentNumber;
    }

    public String getInvoicePostalCode() {
        return invoicePostalCode;
    }

    public String getInvoiceCityName() {
        return invoiceCityName;
    }

    public String getComments() {
        return comments;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public Boolean getDriverExclude() {
        return driverExclude;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    @Override
    public String toString() {
        return "OrderPutRequest{" +
                "orderStatusId=" + orderStatusId +
                ", email='" + email + '\'' +
                ", invoiceIssuedStatus=" + invoiceIssuedStatus +
                ", receipt=" + receipt +
                ", demandingCustomer=" + demandingCustomer +
                ", paymentMethodId=" + paymentMethodId +
                ", paymentByCard=" + paymentByCard +
                ", paymentStatusId=" + paymentStatusId +
                ", deliveryMethodId=" + deliveryMethodId +
                ", groupOrders=" + groupOrders +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerStreet='" + customerStreet + '\'' +
                ", customerBuildingNumber='" + customerBuildingNumber + '\'' +
                ", customerApartmentNumber='" + customerApartmentNumber + '\'' +
                ", customerPostalCode='" + customerPostalCode + '\'' +
                ", customerCityId=" + customerCityId +
                ", hoursTo=" + hoursTo +
                ", weekendHours=" + weekendHours +
                ", weekendHoursTo=" + weekendHoursTo +
                ", weekendAddress=" + weekendAddress +
                ", weekendStreet='" + weekendStreet + '\'' +
                ", weekendBuildingNumber='" + weekendBuildingNumber + '\'' +
                ", weekendApartmentNumber='" + weekendApartmentNumber + '\'' +
                ", weekendPostalCode='" + weekendPostalCode + '\'' +
                ", weekendCityId=" + weekendCityId +
                ", invoiceWanted=" + invoiceWanted +
                ", invoiceCompanyName='" + invoiceCompanyName + '\'' +
                ", invoiceNip='" + invoiceNip + '\'' +
                ", invoiceStreet='" + invoiceStreet + '\'' +
                ", invoiceBuildingNumber='" + invoiceBuildingNumber + '\'' +
                ", invoiceApartmentNumber='" + invoiceApartmentNumber + '\'' +
                ", invoicePostalCode='" + invoicePostalCode + '\'' +
                ", invoiceCityName='" + invoiceCityName + '\'' +
                ", comments='" + comments + '\'' +
                ", adminComments='" + adminComments + '\'' +
                ", driverExclude=" + driverExclude +
                ", startDate=" + startDate +
                ", stopDate=" + stopDate +
                ", cancelReason='" + cancelReason + '\'' +
                '}';
    }
}
