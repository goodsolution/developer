package pl.com.goodsolution.adviser.domain;

import java.time.LocalDateTime;

public class OrderPaymentStatusData {
    private Long userId;
    private LocalDateTime date;

    public OrderPaymentStatusData(Long userId, LocalDateTime date) {
        this.userId = userId;
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
