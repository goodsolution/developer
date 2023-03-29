package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
public class CourseCustomersService {

    private CourseCustomersJdbcRepository courseCustomersJdbcRepository;
    private EmailService emailService;
    private CustomerAuthoritiesService customerAuthoritiesService;
    private AuthorsService authorsService;
    private CustomerToGroupService customerToGroupService;

    public CourseCustomersService(CourseCustomersJdbcRepository courseCustomersJdbcRepository, EmailService emailService, CustomerAuthoritiesService customerAuthoritiesService, AuthorsService authorsService, CustomerToGroupService customerToGroupService) {
        this.courseCustomersJdbcRepository = courseCustomersJdbcRepository;
        this.emailService = emailService;
        this.customerAuthoritiesService = customerAuthoritiesService;
        this.authorsService = authorsService;
        this.customerToGroupService = customerToGroupService;
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

    public List<CustomerData> findWithDetails(CustomersFilter filter) {
        List<CustomerData> customers = courseCustomersJdbcRepository.find(filter);
        List<CustomerData> customersWithDetails = new ArrayList<>();
        if (customers == null) {
            customersWithDetails = null;
        } else {
            for (CustomerData customer : customers) {
                customersWithDetails.add(new CustomerData(customer, customerAuthoritiesService.getAuthoritiesForCustomer(customer), customerToGroupService.find(new CustomerToGroupFilter(null, customer.getId(), null, null)))
                        );
            }
        } return customersWithDetails;
    }

    public void update(CustomerData data) {
        validate(data);
        if (data.getAuthorities() != null) {
            customerAuthoritiesService.deleteAuthorities(data);
            customerAuthoritiesService.validateAndCreateAuthorityForCustomer(data, data.getAuthorities());
        } else {
            data = new CustomerData(data, customerAuthoritiesService.getAuthoritiesForCustomer(data),null);
        }
        courseCustomersJdbcRepository.update(data);
        customerToGroupService.delete(data.getId());
        assignGroupsToCustomer(data, data.getId());

    }

    public void delete(Long id) {
        CustomerData customerData = find(new CustomersFilter(id)).get(0);
        courseCustomersJdbcRepository.update(new CustomerData(customerData, LocalDateTime.now()));
    }

    public void registerCustomer(CustomerData customerCreateRequest) {

        validate(customerCreateRequest);

        if (isEmailTaken(customerCreateRequest.getLogin())) {
            addCustomerAuthorityToAccount(customerCreateRequest);
        } else {
            createNewCustomerAccount(customerCreateRequest);
        }
    }

    public void registerTeacher(CustomerData customerCreateRequest) {

        validate(customerCreateRequest);
        authorsService.validate(customerCreateRequest.getAuthorFirstName(), customerCreateRequest.getAuthorLastName());

        if (isEmailTaken(customerCreateRequest.getLogin())) {
            addTeacherAuthorityToAccount(customerCreateRequest);
        } else {
            createTeacherNewAccount(customerCreateRequest);
        }
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

    public void changeLoggedUsersPassword(String actualPasswordHash, String newPasswordHash) {
        CustomerData customer = getLoggedCustomer();
        if (actualPasswordHash.equals(customer.getPasswordHash())) {
            changePassword(customer, newPasswordHash);
        } else {
            throw new IllegalArgumentException("Wrong actual password");
        }
    }

    public void changePasswordAdmin(Long id, String newPasswordHash) {
        CustomerData customer = get(id);
        changePassword(customer, newPasswordHash);
    }

    public void changePassword(CustomerData customer, String newPasswordHash) {
        validatePasswordChange(newPasswordHash, customer.getPasswordHash());
        update(new CustomerData(customer, newPasswordHash));
        SecurityContextHolder.getContext().setAuthentication(null);
        emailService.sendAfterPasswordChangeMail(customer);
    }

    public void updateLoggedCustomer(String language, Boolean newsletterAccepted) {
        CustomerData loggedCustomer = getLoggedCustomer();
        CustomerData updatedCustomer = new CustomerData(loggedCustomer, language, newsletterAccepted);
        update(updatedCustomer);
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

    private void createTeacherNewAccount(CustomerData teacherCreateRequest) {
        CustomerData customer = new CustomerData(teacherCreateRequest.getLogin(), teacherCreateRequest.getPasswordHash(), teacherCreateRequest.getLanguage(), teacherCreateRequest.getRegulationAccepted(), teacherCreateRequest.getNewsletterAccepted(), true, teacherCreateRequest.getRegistrationDatetime(), teacherCreateRequest.getRegistrationIp(), teacherCreateRequest.getRegistrationUserAgent(), false);
        Long customerId = create(customer);
        CustomerData createdCustomer = find(new CustomersFilter(customerId)).get(0);
//        customerAuthoritiesService.validateAndCreateAuthorityForCustomer(createdCustomer, CustomerAuthority.TEACHER);
        emailService.sendAfterRegistrationMail(createdCustomer, Locale.forLanguageTag(teacherCreateRequest.getLanguage()));
        createAuthorForCustomer(createdCustomer, teacherCreateRequest.getAuthorFirstName(), teacherCreateRequest.getAuthorLastName());
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

    private void createNewCustomerAccount(CustomerData customerCreateRequest) {
        CustomerData customer = new CustomerData(customerCreateRequest.getLogin(), customerCreateRequest.getPasswordHash(), customerCreateRequest.getLanguage(), customerCreateRequest.getRegulationAccepted(), customerCreateRequest.getNewsletterAccepted(), customerCreateRequest.getEnabled(), customerCreateRequest.getInvoiceFirstAndLastName(), customerCreateRequest.getRegistrationDatetime(), customerCreateRequest.getRegistrationIp(), customerCreateRequest.getRegistrationUserAgent(), customerCreateRequest.getEmailConfirmed());
        Long customerId = create(customer);
        assignGroupsToCustomer(customerCreateRequest, customerId);
        CustomerData createdCustomer = find(new CustomersFilter(customerId)).get(0);
        customerAuthoritiesService.validateAndCreateAuthorityForCustomer(createdCustomer, customerCreateRequest.getAuthorities());
        emailService.sendAfterRegistrationMail(createdCustomer, Locale.forLanguageTag(customerCreateRequest.getLanguage()));
    }

    private void assignGroupsToCustomer(CustomerData customerCreateRequest, Long customerId) {
        for (CustomerToGroupData group : customerCreateRequest.getGroups()) {
            customerToGroupService.create(new CustomerToGroupData(customerId, group.getCustomerGroupId()));
        }
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
