package pl.com.mike.developer.api;

import java.util.ArrayList;
import java.util.List;

public class OrderChangeResultGetResponse {
    List<OrderChangeGetResponse> changes = new ArrayList<>();

    public OrderChangeResultGetResponse(List<OrderChangeGetResponse> changes) {
        this.changes = changes;
    }

    public List<OrderChangeGetResponse> getChanges() {
        return changes;
    }
}
