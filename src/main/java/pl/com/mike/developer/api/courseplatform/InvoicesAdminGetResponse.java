package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class InvoicesAdminGetResponse {
    List<InvoiceGetResponse> invoices;

    public InvoicesAdminGetResponse(List<InvoiceGetResponse> invoices) {
        this.invoices = invoices;
    }

    public List<InvoiceGetResponse> getInvoices() {
        return invoices;
    }
}
