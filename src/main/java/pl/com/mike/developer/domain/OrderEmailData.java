package pl.com.mike.developer.domain;

import java.time.LocalDateTime;

public class OrderEmailData {
    private Long no;
    private Long id;
    private String title;
    private String sentBy;
    private LocalDateTime dateTime;
    private Long orderId;
    private String message;

    public OrderEmailData(Long no, Long id, String title, String sentBy, LocalDateTime dateTime, Long orderId, String message) {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}
