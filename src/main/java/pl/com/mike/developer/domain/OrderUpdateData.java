package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderUpdateData {
    private Long id;
    private Long deliveryMethodId;
    private Long paymentMethodId;
    private Long paymentStatusId;
    private Long orderStatusId;
    private Boolean demandingCustomer;
    private Boolean receipt;
    private Boolean invoice;
    private String email;
    private Boolean driverExclude;
    private String comments;
    private String adminComments;
    private Boolean paymentByCard;

    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerStreet;
    private String customerBuildingNumber;
    private String customerApartmentNumber;
    private String customerPostalCode;
    private Long customerCityId;

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

    private LocalTime hoursOf;
    private LocalTime hoursTo;
    private Boolean weekendHours;
    private LocalTime weekendHoursTo;
    private LocalTime weekendHoursOf;

    private LocalDate startDate;
    private LocalDate stopDate;
    private String cancelReason;
    private Boolean groupOrders;
    private Long commentVersion;
    private BigDecimal cityDiscount;
    private String changeStatusSource;

    public OrderUpdateData(Boolean demandingCustomer) {
        this.demandingCustomer = demandingCustomer;
    }

    public OrderUpdateData(Long id, Long deliveryMethodId, Long paymentMethodId, Long paymentStatusId, Long orderStatusId, Boolean demandingCustomer, Boolean receipt, Boolean invoice, String email, Boolean driverExclude, String comments, String adminComments, Boolean paymentByCard, String customerFirstName, String customerLastName, String customerPhone, String customerStreet, String customerBuildingNumber, String customerApartmentNumber, String customerPostalCode, Long customerCityId, Boolean weekendAddress, String weekendStreet, String weekendBuildingNumber, String weekendApartmentNumber, String weekendPostalCode, Long weekendCityId, Boolean invoiceWanted, String invoiceCompanyName, String invoiceNip, String invoiceStreet, String invoiceBuildingNumber, String invoiceApartmentNumber, String invoicePostalCode, String invoiceCityName, LocalTime hoursTo, Boolean weekendHours, LocalTime weekendHoursTo, LocalDate startDate, LocalDate stopDate, String cancelReason, Boolean groupOrders, Long commentVersion) {
        this.id = id;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.paymentStatusId = paymentStatusId;
        this.orderStatusId = orderStatusId;
        this.demandingCustomer = demandingCustomer;
        this.receipt = receipt;
        this.invoice = invoice;
        this.email = email;
        this.driverExclude = driverExclude;
        this.comments = comments;
        this.adminComments = adminComments;
        this.paymentByCard = paymentByCard;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerStreet = customerStreet;
        this.customerBuildingNumber = customerBuildingNumber;
        this.customerApartmentNumber = customerApartmentNumber;
        this.customerPostalCode = customerPostalCode;
        this.customerCityId = customerCityId;
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
        this.hoursTo = hoursTo;
        this.weekendHours = weekendHours;
        this.weekendHoursTo = weekendHoursTo;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.cancelReason = cancelReason;
        this.groupOrders = groupOrders;
    }

    public OrderUpdateData(Long id, Long deliveryMethodId, Long paymentMethodId, Long paymentStatusId, Long orderStatusId, Boolean demandingCustomer, Boolean receipt, Boolean invoice, String email, Boolean driverExclude, String comments, String adminComments, Boolean paymentByCard, String customerFirstName, String customerLastName, String customerPhone, String customerStreet, String customerBuildingNumber, String customerApartmentNumber, String customerPostalCode, Long customerCityId, Boolean weekendAddress, String weekendStreet, String weekendBuildingNumber, String weekendApartmentNumber, String weekendPostalCode, Long weekendCityId, Boolean invoiceWanted, String invoiceCompanyName, String invoiceNip, String invoiceStreet, String invoiceBuildingNumber, String invoiceApartmentNumber, String invoicePostalCode, String invoiceCityName, LocalTime hoursTo, Boolean weekendHours, LocalTime weekendHoursTo, LocalDate startDate, LocalDate stopDate, String cancelReason, Boolean groupOrders, Long commentVersion, String changeStatusSource) {
        this.id = id;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.paymentStatusId = paymentStatusId;
        this.orderStatusId = orderStatusId;
        this.demandingCustomer = demandingCustomer;
        this.receipt = receipt;
        this.invoice = invoice;
        this.email = email;
        this.driverExclude = driverExclude;
        this.comments = comments;
        this.adminComments = adminComments;
        this.paymentByCard = paymentByCard;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerStreet = customerStreet;
        this.customerBuildingNumber = customerBuildingNumber;
        this.customerApartmentNumber = customerApartmentNumber;
        this.customerPostalCode = customerPostalCode;
        this.customerCityId = customerCityId;
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
        this.hoursTo = hoursTo;
        this.weekendHours = weekendHours;
        this.weekendHoursTo = weekendHoursTo;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.cancelReason = cancelReason;
        this.groupOrders = groupOrders;
        this.changeStatusSource = changeStatusSource;
    }


    public Long getId() {
        return id;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getDriverExclude() {
        return driverExclude;
    }

    public String getComments() {
        return comments;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public Boolean getPaymentByCard() {
        return paymentByCard;
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

    public LocalTime getHoursTo() {
        return hoursTo;
    }

    public Boolean getWeekendHours() {
        return weekendHours;
    }

    public LocalTime getWeekendHoursTo() {
        return weekendHoursTo;
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

    public Boolean getGroupOrders() {
        return groupOrders;
    }

    public Long getCommentVersion() {
        return commentVersion;
    }

    public void setCommentVersion(Long commentVersion) { //TODO chane
        this.commentVersion = commentVersion;
    }

    public LocalTime getWeekendHoursOf() {
        return weekendHoursOf;
    }

    public void setWeekendHoursOf(LocalTime weekendHoursOf) {
        this.weekendHoursOf = weekendHoursOf;
    }

    public void setHoursOf(LocalTime hoursOf) {
        this.hoursOf = hoursOf;
    }

    public LocalTime getHoursOf() {
        return hoursOf;
    }

    public BigDecimal getCityDiscount() {
        return cityDiscount;
    }

    public void setCityDiscount(BigDecimal cityDiscount) {
        this.cityDiscount = cityDiscount;
    }

    public String getChangeStatusSource() {
        return changeStatusSource;
    }
}
