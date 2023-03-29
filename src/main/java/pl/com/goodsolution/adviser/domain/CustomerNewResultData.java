package pl.com.goodsolution.adviser.domain;

import java.util.List;

public class CustomerNewResultData {
   private Long count;
   private List<CustomerNewData> newCustomers;

    public CustomerNewResultData(Long count, List<CustomerNewData> newCustomers) {
        this.count = count;
        this.newCustomers = newCustomers;
    }

    public Long getCount() {
        return count;
    }

    public List<CustomerNewData> getNewCustomers() {
        return newCustomers;
    }
}
