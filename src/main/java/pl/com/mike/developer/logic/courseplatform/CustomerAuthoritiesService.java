package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerAuthoritiesFilter;
import pl.com.mike.developer.domain.courseplatform.CustomerAuthorityData;
import pl.com.mike.developer.domain.courseplatform.CustomerData;

import java.util.List;

@Service
public class CustomerAuthoritiesService {

    private CustomerAuthoritiesJdbcRepository customerAuthoritiesJdbcRepository;

    public CustomerAuthoritiesService(CustomerAuthoritiesJdbcRepository customerAuthoritiesJdbcRepository) {
        this.customerAuthoritiesJdbcRepository = customerAuthoritiesJdbcRepository;
    }

    public void deleteAuthorities(CustomerData customer){
        customerAuthoritiesJdbcRepository.deleteAuthoritiesFromCustomer(new CustomerAuthoritiesFilter(customer.getId()));
    }

    public void validateAndCreateAuthorityForCustomer(CustomerData customer, String[] authorities) {
        if (authorities != null) {
            getAuthoritiesWithRoles(authorities);
            customerAuthoritiesJdbcRepository.create(new CustomerAuthorityData(customer, authorities));
        } else {
            throw new IllegalArgumentException("Role can't be empty");
        }
    }

    private void getAuthoritiesWithRoles(String[] authorities) {
        for (int i = 0; i < authorities.length; i++) {
            authorities[i] = CustomerAuthority.valueOf(authorities[i]).getCodeWithRole();
        }
    }

    public Boolean hasCustomerAuthority(CustomerData customer, CustomerAuthority authority) {
        List<CustomerAuthorityData> authorities = customerAuthoritiesJdbcRepository.find(new CustomerAuthoritiesFilter(customer.getId(), authority.getCodeWithRole()));
        return !authorities.isEmpty();
    }

    public String[] getAuthoritiesForCustomer(CustomerData customer) {
        List<CustomerAuthorityData> authorityData = customerAuthoritiesJdbcRepository.find(new CustomerAuthoritiesFilter(customer.getId()));
        if (authorityData != null) {
            return getAuthoritiesForCustomer(authorityData);
        } else {
            throw new IllegalArgumentException("Authorities can't be empty");
        }
    }

    private String[] getAuthoritiesForCustomer(List<CustomerAuthorityData> authorityData) {
        String[] authorities = new String[authorityData.size()];
        int i = 0;
        for (CustomerAuthorityData data : authorityData) {
            authorities[i] = CustomerAuthority.findByCodeWithRole(data.getAuthority()).getCode();
            i++;
        }
        return authorities;
    }
}
