package pl.com.mike.developer.api;

import pl.com.mike.developer.domain.OrderDetailsData;

public class OrderGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String discountCode;
    private String discountValue;


    public OrderGetResponse(Long id) {
        this.id = id;
    }

    public OrderGetResponse(OrderDetailsData data) {
        this.id = data.getId();
        this.firstName = data.getCustomerForOrder().getFirstName();
        this.lastName = data.getCustomerForOrder().getLastName();
        this.email = data.getEmail();
        this.discountCode = data.getDiscountCode();
        this.discountValue = data.getDiscountValue();
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getDiscountValue() {
        return discountValue;
    }
}
