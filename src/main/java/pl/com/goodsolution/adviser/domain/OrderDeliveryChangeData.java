package pl.com.goodsolution.adviser.domain;

import java.time.LocalDateTime;

public class OrderDeliveryChangeData {
    private Long id;
    private Long no;
    private String type;
    private String before;
    private String after;
    private String productName;
    private String userName;
    private LocalDateTime dateTime;

    public OrderDeliveryChangeData(Long id, Long no, String type, String before, String after, String productName, String userName, LocalDateTime dateTime) {
        this.id = id;
        this.no = no;
        this.type = type;
        this.before = before;
        this.after = after;
        this.productName = productName;
        this.userName = userName;
        this.dateTime = dateTime;
    }


    public Long getNo() {
        return no;
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

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getId() {
        return id;
    }
}
