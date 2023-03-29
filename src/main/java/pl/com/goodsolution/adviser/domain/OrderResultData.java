package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderResultData {
   private List<OrderData> orders = new ArrayList<>();
   private BigDecimal sum = BigDecimal.ZERO;
   private Long count;

    public OrderResultData(List<OrderData> orders, BigDecimal sum, Long count) {
        this.orders = orders;
        this.sum = sum;
        this.count = count;
    }

    public List<OrderData> getOrders() {
        return orders;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public Long getCount() {
        return count;
    }
}
