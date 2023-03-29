package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerGroupsFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerToGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerToGroupFilter;

import java.util.List;

@Service
public class CustomerToGroupService {

    private CustomerToGroupJdbcRepository customerToGroupJdbcRepository;

    public CustomerToGroupService(CustomerToGroupJdbcRepository customerToGroupJdbcRepository) {
        this.customerToGroupJdbcRepository = customerToGroupJdbcRepository;
    }

    public Long create(CustomerToGroupData data) {
        return customerToGroupJdbcRepository.create(data);
    }

    public List<CustomerToGroupData> find(CustomerToGroupFilter filter) {
        return customerToGroupJdbcRepository.find(filter);
    }

    public void update(CustomerToGroupData data) {
        customerToGroupJdbcRepository.update(data);
    }

    public void delete(Long id) {
        customerToGroupJdbcRepository.delete(id);
    }


}
