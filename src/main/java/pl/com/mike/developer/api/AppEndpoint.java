package pl.com.mike.developer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.*;
import pl.com.mike.developer.logic.CustomersService;
import pl.com.mike.developer.logic.DictionariesService;
import pl.com.mike.developer.logic.DictionaryType;
import pl.com.mike.developer.logic.Language;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AppEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AppEndpoint.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd");
    private static final DateTimeFormatter TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern("H:mm");

    private CustomersService customersService;

    private DictionariesService dictionariesService; //TODO remove from controller

    public AppEndpoint(CustomersService customersService, DictionariesService dictionariesService) {
        this.customersService = customersService;
        this.dictionariesService = dictionariesService;
    }

    @GetMapping(path = "/api/orders/ordersCount", produces = "application/json; charset=UTF-8")
    public OrderCountGetResponse countOrders() {
        return new OrderCountGetResponse(1234L);
    }

    @GetMapping(path = "/api/product/demand", produces = "application/json; charset=UTF-8")
    public List<DemandsGetResponse> findProductDemands(
            @RequestParam(value = "date", required = false) String date
    ) {
        return Arrays.asList(new DemandsGetResponse(
                1L));
    }

    @GetMapping(path = "/api/customer/substring/{substring}", produces = "application/json; charset=UTF-8")
    public List<CustomerGetResponse> getCustomersBySubstring(@PathVariable String substring) {
        List<DictionaryData> dictionaryCustomers = dictionariesService.getDictionaryCustomers(substring);
        List<CustomerGetResponse> list = new ArrayList<>();
        for (DictionaryData data : dictionaryCustomers) {
            list.add(new CustomerGetResponse(data.getId(), data.getValue(), data.getExtraString()));
        }
        return list;
    }

    @GetMapping(path = "/api/customer/group/{id}", produces = "application/json; charset=UTF-8")
    public CustomerGroupGetResponse getCustomerGroup(@PathVariable Long id) {
        return new CustomerGroupGetResponse(1L);
    }

    @GetMapping(path = "/api/user/{id}", produces = "application/json; charset=UTF-8")
    public UserGetResponse getUser(@PathVariable Long id) {
        return new UserGetResponse(1L);
    }

    @GetMapping(path = "/api/customers-new", produces = "application/json; charset=UTF-8")
    public CustomerNewResultGetResponse getNewCustomers(
            @RequestParam(name = "date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(name = "order_status_id", required = false) Long orderStatusId,
            @RequestParam(name = "diet_id", required = false) Long[] dietIds
    ) {
        return customersService.findNewCustomersAll(new CustomerNewFilter(dateFrom, dateTo, dietIds, orderStatusId));
    }

    @GetMapping(path = "/api/dictionary/{name}", produces = "application/json; charset=UTF-8")
    public List<DictionaryData> getDictionary(@PathVariable String name) {
        return dictionariesService.getDictionary(DictionaryType.valueOf(name.toUpperCase()), Language.PL);
    }

    @PostMapping(path = "/api/customer-groups", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createCustomerGroup(@RequestBody CustomerGroupPostRequest req) {
        log.trace("Create new customer group with the following data: ");
        log.trace("req: " + req);
        customersService.createCustomerGroup(new CustomerGroupData(req.getName(), req.getDiscount(), req.getStatus()));
    }

    @GetMapping(path = "/api/customer-groups", produces = "application/json; charset=UTF-8")
    public CustomerGroupResultGetResponse findCustomerGroups(
            @RequestParam(name = "sort_by", required = false, defaultValue = "group_id") String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "order_by", required = false, defaultValue = "ASC") String orderBy) {
        return customersService.findCustomerGroupsAll(new CustomerGroupsFilter(sortBy, page, pageSize, orderBy));
    }

    @PutMapping(path = "/api/customer-groups/{groupId}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateCustomerGroup(@PathVariable Long groupId,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "discount", required = false) Integer discount,
                                    @RequestParam(name = "status", required = false) Integer status) {
        customersService.updateCustomerGroup(getCustomerGroupToUpdate(groupId, name, discount, status));
    }

    private CustomerGroupData getCustomerGroupToUpdate(Long groupId, String name, Integer discount, Integer status) {

        CustomerGroupData oldData = customersService.getCustomerGroup(groupId);

        if (name == null) {
            name = oldData.getName();
        }
        if (discount == null) {
            discount = oldData.getDiscount();
        }
        if (status == null) {
            status = oldData.getStatus();
        }
        return new CustomerGroupData(groupId, name, discount, status);
    }

    @DeleteMapping(path = "/api/customer-groups/{groupId}")
    public void deleteCustomerGroup(@PathVariable Long groupId) {
        log.trace("delete the specified customer group (" + groupId + ").");
        customersService.deleteCustomerGroup(groupId);
    }

    @GetMapping(path = "/api/customers", produces = "application/json; charset=UTF-8")
    public CustomersResultGetResponse findCustomers(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "name_and_surname", required = false) String nameAndSurname,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "group_id", required = false) Long groupId,
            @RequestParam(name = "status_id", required = false) Long statusId,
            @RequestParam(name = "sort_records_by", required = false) String sortRecordsBy,
            @RequestParam(name = "records_arrangement", required = false) String recordArrangement) {
        List<CustomerData> customers = customersService.findCustomers(new CustomersFilter(page, pageSize, nameAndSurname, email, groupId, statusId, sortRecordsBy, recordArrangement));
        return new CustomersResultGetResponse(customersService.getCustomersCount(), customersDataToResponses(customers));
    }

    private List<CustomersGetResponse> customersDataToResponses(List<CustomerData> data) {
        List<CustomersGetResponse> responses = new ArrayList<>();
        for (CustomerData datum : data) {
            responses.add(customerDataToResponse(datum));
        }
        return responses;
    }

    private CustomersGetResponse customerDataToResponse(CustomerData data) {
        return new CustomersGetResponse(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getFirstAddressLine(),
                data.getSecondAddressLine(),
                data.getBuildingNumber(),
                data.getApartmentNumber(),
                data.getPostalCode(),
                data.getCity(),
                data.getEmail(),
                data.getRegistrationDate(),
                data.getType(),
                data.getGroupId(),
                data.getActive(),
                data.getOrdersCount());
    }

    @DeleteMapping(path = "/api/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        log.trace("delete the specified customer (" + customerId + ").");
        customersService.deleteCustomer(customerId);
    }

    @PostMapping(path = "/api/customers", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public Long createCustomer(@RequestBody CustomerPostRequest req) {
        log.trace("Create new customer with the following data: ");
        log.trace("req: " + req);
        return customersService.createCustomer(new CustomerData(req.getName(), req.getSurname(), req.getStreet(), req.getHouseNumber(), req.getApartmentNumber(), req.getPostalCode(), req.getCity(), req.getMail(), req.getGroup(), req.getStatus(), req.getCustomerFrom(), req.getType()));
    }

}
