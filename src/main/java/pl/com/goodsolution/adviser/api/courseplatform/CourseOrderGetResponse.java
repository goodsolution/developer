package pl.com.goodsolution.adviser.api.courseplatform;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseOrderData;

public class CourseOrderGetResponse {
    private Long id;
    private String number;
    private CustomerGetResponse customer;
    private String purchaseDate;
    private String totalPrice;
    private String status;
    private String payuPaymentUrl;

    public CourseOrderGetResponse(CourseOrderData data) {
        this.id = data.getId();
        this.number = data.getNumber();
        this.customer = new CustomerGetResponse(data.getCustomer());
        this.purchaseDate = data.getPurchaseDate().toString();
        this.totalPrice = data.getTotalPrice().toString();
        this.status = data.getStatus();
        this.payuPaymentUrl = data.getPayuPaymentUrl();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public CustomerGetResponse getCustomer() {
        return customer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getPayuPaymentUrl() {
        return payuPaymentUrl;
    }
}
