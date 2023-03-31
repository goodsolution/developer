package pl.com.mike.developer.logic.courseplatform;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.AuthorData;
import pl.com.mike.developer.domain.courseplatform.AuthorsFilter;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CourseCustomersService {

    private CourseCustomersJdbcRepository courseCustomersJdbcRepository;

    private CustomerAuthoritiesService customerAuthoritiesService;
    private AuthorsService authorsService;


    public CourseCustomersService(CourseCustomersJdbcRepository courseCustomersJdbcRepository, CustomerAuthoritiesService customerAuthoritiesService, AuthorsService authorsService) {
        this.courseCustomersJdbcRepository = courseCustomersJdbcRepository;

        this.customerAuthoritiesService = customerAuthoritiesService;
        this.authorsService = authorsService;

    }

    public Long create(CustomerData customer) {
        validate(customer);
        String ip = prepareRegistrationIp(customer.getRegistrationIp());
        String userAgent = prepareUserAgent(customer.getRegistrationUserAgent());
        return courseCustomersJdbcRepository.create(new CustomerData(customer, ip, userAgent));
    }

    public CustomerData get(Long id) {
        return find(new CustomersFilter(id)).get(0);
    }

    public List<CustomerData> find(CustomersFilter filter) {
        return courseCustomersJdbcRepository.find(filter);
    }





    public void delete(Long id) {
        CustomerData customerData = find(new CustomersFilter(id)).get(0);
        courseCustomersJdbcRepository.update(new CustomerData(customerData, LocalDateTime.now()));
    }

    public CustomerData getLoggedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<CustomerData> list = courseCustomersJdbcRepository.find(new CustomersFilter(authentication.getName()));
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Boolean isLifeAdviserUser() {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.LIFE_ADVISER_USER);
    }

    public Boolean isLoggedCustomerAdmin() {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.ADMIN);
    }

    public Boolean isLoggedCustomerTeacher() {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.TEACHER);
    }

    public Boolean isLoggedCustomerUser() {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.USER);
    }

    public Boolean isLoggedCustomerStudent() {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.STUDENT);
    }

    public Boolean isLoggedWithRole(String role) {
        return customerAuthoritiesService.hasCustomerAuthority(getLoggedCustomer(), CustomerAuthority.valueOf(role.toUpperCase()));
    }







    public List<CustomerData> findConfirmEmailNotificationRecipients() {
        return courseCustomersJdbcRepository.findConfirmEmailNotificationRecipients();
    }

    private String prepareUserAgent(String userAgent) {
        if (userAgent == null) {
            return null;
        } else if (userAgent.length() > 1000) {
            return "User agent too long to save it";
        } else {
            return userAgent;
        }
    }

    private String prepareRegistrationIp(String ip) {
        if (ip == null) {
            return null;
        } else if (ip.length() > 200) {
            return "Ip too long to save it";
        } else {
            return ip;
        }
    }

    private void validate(CustomerData data) {
        validateEmail(data.getLogin());
        validatePassword(data.getPasswordHash());
        validateRegulationAccept(data.getRegulationAccepted());
        validateNewsletterAccept(data.getNewsletterAccepted());
    }

    private void validateEmail(String email) {

        EmailUtil.validateEmail(email);

        if (email.length() > 255) {
            throw new IllegalArgumentException("Email too long, max 255 characters");
        }

    }

    private void validateIfEmailTaken(String email) {
        if (!courseCustomersJdbcRepository.find(new CustomersFilter(email)).isEmpty()) {
            throw new IllegalArgumentException("Email taken. Try again with other email.");
        }
    }

    private Boolean isEmailTaken(String email) {
        return !courseCustomersJdbcRepository.find(new CustomersFilter(email)).isEmpty();
    }

    private void validatePassword(String passwordHash) {
        if (passwordHash == null || passwordHash.equals("")) {
            throw new IllegalArgumentException("Password can't be empty");
        } else if (passwordHash.length() > 200) {
            throw new IllegalArgumentException("Password too long");
        }
    }

    private void validateRegulationAccept(Boolean regulationAccepted) {
        if (regulationAccepted == null || !regulationAccepted) {
            throw new IllegalArgumentException("You must accept regulation");
        }
    }

    private void validateNewsletterAccept(Boolean newsletterAccepted) {
        if (newsletterAccepted == null) {
            throw new IllegalArgumentException("Newsletter acceptation can't be empty");
        }
    }

    private void validatePasswordChange(String newPasswordHash, String oldPasswordHash) {
        if (oldPasswordHash.equals(newPasswordHash)) {
            throw new IllegalArgumentException("New password can't be the same as old password");
        }
    }



    private void createAuthorForCustomer(CustomerData customer, String authorFirstName, String authorLastName) {
        AuthorData author = new AuthorData(authorFirstName, authorLastName);
        Long authorId = authorsService.create(author);
        AuthorData createdAuthor = authorsService.find(new AuthorsFilter(authorId)).get(0);
        courseCustomersJdbcRepository.update(new CustomerData(customer, createdAuthor));
    }

    private void addTeacherAuthorityToAccount(CustomerData teacherCreateRequest) {
        CustomerData customer = find(new CustomersFilter(teacherCreateRequest.getLogin())).get(0);
        if (customer.getLogin().equals(teacherCreateRequest.getLogin()) && customer.getPasswordHash().equals(teacherCreateRequest.getPasswordHash())) {
//            customerAuthoritiesService.validateAndCreateAuthorityForCustomer(customer, CustomerAuthority.TEACHER);
        } else {
            throw new IllegalArgumentException("Email or password incorrect");
        }
        createAuthorForCustomer(customer, teacherCreateRequest.getAuthorFirstName(), teacherCreateRequest.getAuthorLastName());
    }

    private void addCustomerAuthorityToAccount(CustomerData customerCreateRequest) {
        CustomerData customer = find(new CustomersFilter(customerCreateRequest.getLogin())).get(0);
        if (customer.getLogin().equals(customerCreateRequest.getLogin()) && customer.getPasswordHash().equals(customerCreateRequest.getPasswordHash())) {
//            customerAuthoritiesService.validateAndCreateAuthorityForCustomer(customer, CustomerAuthority.USER);
            customerAuthoritiesService.validateAndCreateAuthorityForCustomer(customer, customerCreateRequest.getAuthorities());
        } else {
            throw new IllegalArgumentException("Email or password incorrect");
        }
    }

}
