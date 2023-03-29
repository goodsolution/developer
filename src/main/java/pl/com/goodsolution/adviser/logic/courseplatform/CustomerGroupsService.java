package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupsFilter;

import java.util.List;

@Service
public class CustomerGroupsService {

    private CustomerGroupsJdbcRepository customerGroupsJdbcRepository;

    public CustomerGroupsService(CustomerGroupsJdbcRepository customerGroupsJdbcRepository) {
        this.customerGroupsJdbcRepository = customerGroupsJdbcRepository;
    }

    public Long create(CustomerGroupData data) {
        validate(data);
        return customerGroupsJdbcRepository.create(data);
    }

    public List<CustomerGroupData> find(CustomerGroupsFilter filter) {
        return customerGroupsJdbcRepository.find(filter);
    }

    public void update(CustomerGroupData data) {
        validate(data);
        customerGroupsJdbcRepository.update(data);
    }

    public void delete(Long id) {
        customerGroupsJdbcRepository.delete(id);
    }

    private void validate(CustomerGroupData data) {
        if (data.getName() == null || data.getName().equals("")) {
            throw new IllegalArgumentException("First name can't be empty");
        } else if (data.getName().length() > 200) {
            throw new IllegalArgumentException("First name too long");
        }
    }
}
