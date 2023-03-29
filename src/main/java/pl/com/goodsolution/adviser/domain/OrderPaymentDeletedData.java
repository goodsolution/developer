package pl.com.goodsolution.adviser.domain;

import java.time.LocalDateTime;

public class OrderPaymentDeletedData {
    private Long paymentId;
    private LocalDateTime dateTime;;
    private Long deletedBy;


    public OrderPaymentDeletedData(Long paymentId, LocalDateTime dateTime, Long deletedBy) {
        this.paymentId = paymentId;
        this.dateTime = dateTime;
        this.deletedBy = deletedBy;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }
}
