package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class OrderDetailsGetResponse {
    private List<PurchasedCourseGetResponse> purchasedCourses;
    private List<InvoiceGetResponse> invoices;
    private CourseOrderGetResponse order;

    public OrderDetailsGetResponse(List<PurchasedCourseGetResponse> purchasedCourses, List<InvoiceGetResponse> invoices, CourseOrderGetResponse order) {
        this.purchasedCourses = purchasedCourses;
        this.invoices = invoices;
        this.order = order;
    }

    public List<PurchasedCourseGetResponse> getPurchasedCourses() {
        return purchasedCourses;
    }

    public List<InvoiceGetResponse> getInvoices() {
        return invoices;
    }

    public CourseOrderGetResponse getOrder() {
        return order;
    }
}
