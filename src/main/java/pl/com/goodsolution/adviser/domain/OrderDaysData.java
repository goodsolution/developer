package pl.com.goodsolution.adviser.domain;

import java.time.LocalDate;

public class OrderDaysData {
    private Long id;
    private String product;
    private LocalDate date;
    private Long orderProductId;
    private Long orderId;
    private String status;
    private String statusName;
    private Long quantity;
    private String deliveryInfo;


    public OrderDaysData(Long id) {
        this.id = id;
    }

    public OrderDaysData(LocalDate date, Long orderProductId, Long orderId, String status) {
        this.date = date;
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.status = status;
    }

    public OrderDaysData(Long id, String product, LocalDate date, String status, String statusName, Long quantity, String deliveryInfo) {
        this.id = id;
        this.product = product;
        this.date = date;
        this.status = status;
        this.statusName = statusName;
        this.quantity = quantity;
        this.deliveryInfo = deliveryInfo;
    }

    public OrderDaysData(Long id, String product, LocalDate date, String status, String statusName, Long quantity) {
        this.id = id;
        this.product = product;
        this.date = date;
        this.status = status;
        this.statusName = statusName;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }
}

