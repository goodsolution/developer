package pl.com.mike.developer.api;

import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryChangeResultGetResponse {
    List<OrderChangeGetResponse> changes = new ArrayList<>();

    public OrderDeliveryChangeResultGetResponse(List<OrderChangeGetResponse> changes) {
        this.changes = changes;
    }

    public List<OrderChangeGetResponse> getChanges() {
        return changes;
    }
}
