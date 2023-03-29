package pl.com.goodsolution.adviser.domain.courseplatform;

public class CourseOrdersFilter {
    private Long id;
    private Long customerId;
    private String number;
    private String status;
    private Boolean invoiceIssued;
    private Long page;
    private Long pageSize;

    public CourseOrdersFilter(Long id) {
        this.id = id;
    }

    public CourseOrdersFilter(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public CourseOrdersFilter(Long id, String number, String status, Boolean invoiceIssued, Long page, Long pageSize) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.invoiceIssued = invoiceIssued;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getInvoiceIssued() {
        return invoiceIssued;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
