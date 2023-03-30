package pl.com.mike.developer.api;

import java.math.BigDecimal;
import java.util.List;

public class OrdersResultGetResponse {
    private BigDecimal sum;
    private Long count;
    private List<OrdersGetResponse> orders;
    private Boolean nextPageAvailable; //or maxPageNumber

    public OrdersResultGetResponse(BigDecimal sum, Long count, List<OrdersGetResponse> orders) {
        this.sum = sum;
        this.count = count;
        this.orders = orders;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public List<OrdersGetResponse> getOrders() {
        return orders;
    }

    public Long getCount() {
        return count;
    }
}
