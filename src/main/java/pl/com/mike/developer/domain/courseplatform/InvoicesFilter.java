package pl.com.mike.developer.domain.courseplatform;

public class InvoicesFilter {
    private Long id;
    private Long orderId;
    private Boolean deleted;

    public InvoicesFilter(Long id, Long orderId, Boolean deleted) {
        this.id = id;
        this.orderId = orderId;
        this.deleted = deleted;
    }

    public InvoicesFilter(Long id, Long orderId) {
        this.id = id;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
