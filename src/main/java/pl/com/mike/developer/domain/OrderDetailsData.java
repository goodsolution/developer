package pl.com.mike.developer.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OrderDetailsData {
    private Long id;
    private String orderId;
    private LocalDateTime orderDate;
    private String ipAddress;
    private String email;
    private String comment;
    private String adminComment;
    private Long statusId;
    private String statusName;
    private Boolean receipt;
    private Boolean demandingCustomer;
    private String demandingCustomerIndicator;
    private Long paymentMethodId;
    private String paymentMethodName;
    private Boolean paymentByCard;
    private Long paymentStatusId;
    private String paymentStatusName;
    private Long shipmentTypeId;
    private LocalDateTime paymentStatusUpdateDateTime;
    private String paymentStatusUpdateUser;
    private Boolean groupOrdersEnabled;
    private Boolean groupOrders;
    private LocalTime hoursTo;
    private LocalTime hoursFrom;
    private Boolean weekendHoursStatus;
    private LocalTime weekendHoursTo;
    private LocalTime weekendHoursFrom;
    private String excludeFromDriverSettlement;
    private OrderCustomerData customerForOrder;
    private OrderWeekendAddressData orderWeekendAddress;
    private OrderInvoiceData orderInvoice;
    private BigDecimal orderBasketSumNo;
    private BigDecimal orderBasketSum;
    private String discountCode;
    private String discountValue;
    private String orderCreationSource;
    private List<OrderCancelledData> statusCancellations;
    private BigDecimal cityDiscount;
    private Boolean exceptionDriver;
    private Long commentVersion;
    private String statusChangeSource;
    private Boolean newCalendar;


    public OrderDetailsData(Long id, String orderId, LocalDateTime orderDate, String ipAddress, String email, String comment, String adminComment, Long statusId, String statusName, Boolean receipt, Boolean demandingCustomer, String demandingCustomerIndicator, Long paymentMethodId, String paymentMethodName, Boolean paymentByCard, Long paymentStatusId, String paymentStatusName, Long shipmentTypeId, LocalDateTime paymentStatusUpdateDateTime, String paymentStatusUpdateUser, Boolean groupOrdersEnabled, Boolean groupOrders, LocalTime hoursTo, LocalTime hoursFrom, Boolean weekendHoursStatus, LocalTime weekendHoursTo, LocalTime weekendHoursFrom, String excludeFromDriverSettlement, OrderCustomerData customerForOrder, OrderWeekendAddressData orderWeekendAddress, OrderInvoiceData orderInvoice, BigDecimal orderBasketSumNo, BigDecimal orderBasketSum, String discountCode, String discountValue, String orderCreationSource, List<OrderCancelledData> statusCancellations, BigDecimal cityDiscount, Boolean exceptionDriver, Long commentVersion, String statusChangeSource, Boolean newCalendar) {
        this.id = id;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.ipAddress = ipAddress;
        this.email = email;
        this.comment = comment;
        this.adminComment = adminComment;
        this.statusId = statusId;
        this.statusName = statusName;
        this.receipt = receipt;
        this.demandingCustomer = demandingCustomer;
        this.demandingCustomerIndicator = demandingCustomerIndicator;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentByCard = paymentByCard;
        this.paymentStatusId = paymentStatusId;
        this.paymentStatusName = paymentStatusName;
        this.shipmentTypeId = shipmentTypeId;
        this.paymentStatusUpdateDateTime = paymentStatusUpdateDateTime;
        this.paymentStatusUpdateUser = paymentStatusUpdateUser;
        this.groupOrdersEnabled = groupOrdersEnabled;
        this.groupOrders = groupOrders;
        this.hoursTo = hoursTo;
        this.hoursFrom = hoursFrom;
        this.weekendHoursStatus = weekendHoursStatus;
        this.weekendHoursTo = weekendHoursTo;
        this.weekendHoursFrom = weekendHoursFrom;
        this.excludeFromDriverSettlement = excludeFromDriverSettlement;
        this.customerForOrder = customerForOrder;
        this.orderWeekendAddress = orderWeekendAddress;
        this.orderInvoice = orderInvoice;
        this.orderBasketSumNo = orderBasketSumNo;
        this.orderBasketSum = orderBasketSum;
        this.discountCode = discountCode;
        this.discountValue = discountValue;
        this.orderCreationSource = orderCreationSource;
        this.statusCancellations = statusCancellations;
        this.cityDiscount = cityDiscount;
        this.exceptionDriver = exceptionDriver;
        this.commentVersion = commentVersion;
        this.statusChangeSource = statusChangeSource;
        this.newCalendar = newCalendar;
    }


    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public Long getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public Boolean getReceipt() {
        return receipt;
    }

    public Boolean getDemandingCustomer() {
        return demandingCustomer;
    }

    public String getDemandingCustomerIndicator() {
        return demandingCustomerIndicator;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public Boolean getPaymentByCard() {
        return paymentByCard;
    }

    public Long getPaymentStatusId() {
        return paymentStatusId;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public Long getShipmentTypeId() {
        return shipmentTypeId;
    }

    public LocalDateTime getPaymentStatusUpdateDateTime() {
        return paymentStatusUpdateDateTime;
    }

    public String getPaymentStatusUpdateUser() {
        return paymentStatusUpdateUser;
    }

    public Boolean getGroupOrdersEnabled() {
        return groupOrdersEnabled;
    }

    public Boolean getGroupOrders() {
        return groupOrders;
    }

    public LocalTime getHoursTo() {
        return hoursTo;
    }

    public Boolean getWeekendHoursStatus() {
        return weekendHoursStatus;
    }

    public LocalTime getWeekendHoursTo() {
        return weekendHoursTo;
    }

    public String getExcludeFromDriverSettlement() {
        return excludeFromDriverSettlement;
    }

    public OrderCustomerData getCustomerForOrder() {
        return customerForOrder;
    }

    public OrderWeekendAddressData getOrderWeekendAddress() {
        return orderWeekendAddress;
    }

    public OrderInvoiceData getOrderInvoice() {
        return orderInvoice;
    }

    public BigDecimal getOrderBasketSumNo() {
        return orderBasketSumNo;
    }

    public BigDecimal getOrderBasketSum() {
        return orderBasketSum;
    }

    public LocalTime getHoursFrom() {
        return hoursFrom;
    }

    public LocalTime getWeekendHoursFrom() {
        return weekendHoursFrom;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public String getOrderCreationSource() {
        return orderCreationSource;
    }

    public List<OrderCancelledData> getStatusCancellations() {
        return statusCancellations;
    }

    public BigDecimal getCityDiscount() {
        return cityDiscount;
    }

    public Boolean getExceptionDriver() {
        return exceptionDriver;
    }

    public String getStatusChangeSource() {
        return statusChangeSource;
    }

    @Override
    public String toString() {
        return "OrderDetailsData{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", ipAddress='" + ipAddress + '\'' +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                ", adminComment='" + adminComment + '\'' +
                ", statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                ", receipt=" + receipt +
                ", demandingCustomer=" + demandingCustomer +
                ", demandingCustomerIndicator='" + demandingCustomerIndicator + '\'' +
                ", paymentMethodId=" + paymentMethodId +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", paymentByCard=" + paymentByCard +
                ", paymentStatusId=" + paymentStatusId +
                ", paymentStatusName='" + paymentStatusName + '\'' +
                ", shipmentTypeId=" + shipmentTypeId +
                ", paymentStatusUpdateDateTime=" + paymentStatusUpdateDateTime +
                ", paymentStatusUpdateUser='" + paymentStatusUpdateUser + '\'' +
                ", groupOrdersEnabled=" + groupOrdersEnabled +
                ", groupOrders=" + groupOrders +
                ", hoursTo='" + hoursTo + '\'' +
                ", hoursFrom='" + hoursFrom + '\'' +
                ", weekendHoursStatus=" + weekendHoursStatus +
                ", weekendHoursTo='" + weekendHoursTo + '\'' +
                ", weekendHoursFrom='" + weekendHoursFrom + '\'' +
                ", excludeFromDriverSettlement='" + excludeFromDriverSettlement + '\'' +
                ", customerForOrder=" + customerForOrder +
                ", orderWeekendAddress=" + orderWeekendAddress +
                ", orderInvoice=" + orderInvoice +
                ", orderBasketSumNo=" + orderBasketSumNo +
                ", orderBasketSum=" + orderBasketSum +
                ", discountCode='" + discountCode + '\'' +
                ", discountValue='" + discountValue + '\'' +
                ", orderCreationSource='" + orderCreationSource + '\'' +
                ", statusCancellations=" + statusCancellations +
                '}';
    }

    public Long getCommentVersion() {
        return commentVersion;
    }

    public OrderUpdateData toOrderUpdateData(Long statusId, String changeStatusSource) {
        return new OrderUpdateData(
                this.id,
                this.shipmentTypeId,
                this.paymentMethodId,
                this.paymentStatusId,
                statusId,
                this.demandingCustomer,
                this.receipt,
                this.orderInvoice.isInvoiceIssuedStatus(),
                null,
                this.exceptionDriver,
                this.comment,
                this.adminComment,
                this.paymentByCard,
                this.customerForOrder.getFirstName(),
                this.customerForOrder.getLastName(),
                this.customerForOrder.getPhoneNumber(),
                this.customerForOrder.getStreet(),
                this.customerForOrder.getBuildingNumber(),
                this.customerForOrder.getApartmentNumber(),
                this.customerForOrder.getPostalCode(),
                this.customerForOrder.getCityId(),
                this.orderWeekendAddress.getWeekendAddressStatus(),
                this.orderWeekendAddress.getStreet(),
                this.orderWeekendAddress.getBuildingNumber(),
                this.orderWeekendAddress.getApartmentNumber(),
                this.orderWeekendAddress.getPostalCode(),
                this.orderWeekendAddress.getCityId(),
                this.orderInvoice.getInvoiceWantedStatus(),
                this.orderInvoice.getCompanyName(),
                this.orderInvoice.getNip(),
                this.orderInvoice.getStreet(),
                this.orderInvoice.getBuildingNumber(),
                this.orderInvoice.getApartmentNumber(),
                this.orderInvoice.getPostalCode(),
                this.orderInvoice.getCityName(),
                this.hoursTo,
                this.weekendHoursStatus,
                this.weekendHoursTo,
                null,
                null,
                null,
                this.groupOrders,
                this.commentVersion,
                changeStatusSource
        );
    }

    public Boolean getNewCalendar() {
        return newCalendar;
    }
}
