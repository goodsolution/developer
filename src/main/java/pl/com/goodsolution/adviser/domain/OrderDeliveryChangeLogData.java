package pl.com.goodsolution.adviser.domain;

public class OrderDeliveryChangeLogData {
    private Long orderId;
    private Long deliveryId;
    private Long productId;
    private String type;
    private String before;
    private String after;
    private Long editedBy;

    public OrderDeliveryChangeLogData(Long orderId, Long deliveryId, Long productId, String type, String before, String after, Long editedBy) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.productId = productId;
        this.type = type;
        this.before = before;
        this.after = after;
        this.editedBy = editedBy;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getType() {
        return type;
    }

    public String getBefore() {
        return before;
    }

    public String getAfter() {
        return after;
    }

    public Long getEditedBy() {
        return editedBy;
    }
}
