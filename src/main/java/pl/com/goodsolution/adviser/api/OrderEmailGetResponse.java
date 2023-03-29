package pl.com.goodsolution.adviser.api;

public class OrderEmailGetResponse {
    private Long no;
    private Long id;
    private String title;
    private String sentBy;
    private String dateTime;
    private Long orderId;
    private String message;

    public OrderEmailGetResponse(Long no, Long id, String title, String sentBy, String dateTime, Long orderId, String message) {
        this.no = no;
        this.id = id;
        this.title = title;
        this.sentBy = sentBy;
        this.dateTime = dateTime;
        this.orderId = orderId;
        this.message = message;
    }

    public Long getNo() {
        return no;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSentBy() {
        return sentBy;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}
