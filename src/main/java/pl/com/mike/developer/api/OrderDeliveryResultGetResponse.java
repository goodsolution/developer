package pl.com.mike.developer.api;

import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryResultGetResponse {
    private List<OrderDeliveryGetResponse> deliveries = new ArrayList<>();

    public OrderDeliveryResultGetResponse(List<OrderDeliveryGetResponse> deliveries) {
        this.deliveries = deliveries;
    }

    public List<OrderDeliveryGetResponse> getDeliveries() {
        return deliveries;
    }
}
