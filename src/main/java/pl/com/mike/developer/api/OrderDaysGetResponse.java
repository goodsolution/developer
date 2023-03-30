package pl.com.mike.developer.api;

public class OrderDaysGetResponse {
    private Long id;
    private String product;
    private String date;
    private String status;
    private String statusName;
    private Long quantity;
    private String deliveryInfo;

    public OrderDaysGetResponse(Long id, String product, String date, String status, String statusName, Long quantity, String deliveryInfo) {
        this.id = id;
        this.product = product;
        this.date = date;
        this.status = status;
        this.statusName = statusName;
        this.quantity = quantity;
        this.deliveryInfo = deliveryInfo;
    }

    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getDate() {
        return date;
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
