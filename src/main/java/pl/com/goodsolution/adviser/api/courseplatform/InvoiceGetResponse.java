package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.InvoiceData;

public class InvoiceGetResponse {
    private Long id;
    private CourseOrderGetResponse order;
    private String type;
    private String fileName;
    private String number;

    public InvoiceGetResponse(InvoiceData data) {
        this.id = data.getId();
        this.order = new CourseOrderGetResponse(data.getOrder());
        this.type = data.getType();
        this.fileName = data.getFileName();
        this.number = data.getNumber();
    }

    public Long getId() {
        return id;
    }

    public CourseOrderGetResponse getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public String getNumber() {
        return number;
    }
}
