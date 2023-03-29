package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.api.*;
import pl.com.goodsolution.adviser.domain.*;
import pl.com.goodsolution.adviser.logic.courseplatform.EmailUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomersService {
    public static final int MAX_DISCOUNT_VALUE = 100;
    public static final int MIN_DISCOUNT_VALUE = 0;
    public static final Long UNREGISTERED_CUSTOMER_GROUP_ID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CustomersService.class);
    private CustomersJdbcRepository customersJdbcRepository;
    private DictionariesService dictionariesService;
    private DriversService driversService;
    private ViewersService viewersService;

    public CustomersService(CustomersJdbcRepository customersJdbcRepository, DictionariesService dictionariesService, DriversService driversService, ViewersService viewersService) {
        this.customersJdbcRepository = customersJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.driversService = driversService;
        this.viewersService = viewersService;
    }

    public CustomerNewResultGetResponse findNewCustomersAll(CustomerNewFilter filter) {
        List<CustomerNewGetResponse> customersNew = findNewCustomers(filter);
        return new CustomerNewResultGetResponse((long) customersNew.size(), customersNew);
    }

    public List<CustomerNewGetResponse> findNewCustomers(CustomerNewFilter finder) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = customersJdbcRepository.findNewCustomers(finder);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findNewCustomers (1)", timeTaken);

        Long no = 1L;
        DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");
        DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");

        List<CustomerNewGetResponse> list = new ArrayList<>();
        for (Map row : rows) {
            CustomerNewGetResponse data = new CustomerNewGetResponse(
                    no++,
                    //TODO What about paging? Are the results from New Customers displayed by pages? There was no suggestion of it on the screenshots.
                    Long.valueOf(String.valueOf(row.get("order_id"))),
                    (String) row.get("customer_name") + " " + (String) row.get("customer_surname"),
                    String.valueOf(row.get("user_order_id")),
                    BigDecimal.valueOf((Float) row.get("order_basket_sum")),
                    dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("order_status_id"))), DictionaryType.ORDER_STATUSES, Language.PL),
                    LocalDateTime.parse(String.valueOf(row.get("order_datatime")), inFormatter).format(outFormatter)
            );
            list.add(data);
        }
        return list;
    }

    public CustomerData getCustomer(Long id) {
        long startTime = System.currentTimeMillis();
        CustomerData data = customersJdbcRepository.getCustomer(id);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "getCustomer", timeTaken);
        if (data == null) {
            throw new IllegalArgumentException("Nie mogę pobrać klienta id: " + id);
        }
        String city = data.getCity();
        Long cityId = (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null;
        Long defaultDriverId = driversService.getDefaultDriverForCity(cityId).getId();
        if (defaultDriverId == null) {
            throw new IllegalArgumentException("defaultDriverId is null");
        }
        String weekendCity = data.getWeekendCity();
        Long weekendCityId = (weekendCity != null && !weekendCity.isEmpty()) ? dictionariesService.getDictionaryIdByValue(weekendCity, DictionaryType.CITIES, Language.PL) : null;

        String customerFrom = prepareCustomerFrom(data.getCustomerFrom());

        return new CustomerData(data, defaultDriverId, cityId, weekendCityId, customerFrom);
    }

    private String prepareCustomerFrom(String customerFrom) {
        ViewerData viewerData;
        if(customerFrom != null) {
            if(customerFrom.equals("admin")) {
                return CustomerFrom.ADMINISTRATION_PANEL.getText();
            } else if(customerFrom.equals("newsletter")) {
                return CustomerFrom.NEWSLETTER.getText();
            } else {
                viewerData = viewersService.get(customerFrom);
                if(viewerData != null) {
                    return getCustomerFrom(viewerData);
                } else {
                    return CustomerFrom.OTHER.getText();
                }
            }
        } else {
            return CustomerFrom.OTHER.getText();
        }
    }

    private String getCustomerFrom(ViewerData viewerData) {
        if(viewerData.getHttpReferer().matches(".*facebook.*")) {
            return CustomerFrom.FACEBOOK.getText();
        } else if(viewerData.getHttpReferer().matches(".*google.*")) {
            return CustomerFrom.GOOGLE.getText();
        } else if(viewerData.getHttpReferer().matches(".*https://masterdieta.pl/.*")) {
            return CustomerFrom.WWW_PAGE.getText();
        } else if(viewerData.getHttpReferer().isEmpty()) {
            return CustomerFrom.NONE.getText();
        } else {
            return CustomerFrom.OTHER.getText();
        }
    }

    public int updateCustomerDemanding(Long customerId, Boolean state) {
        return customersJdbcRepository.updateCustomerDemanding(customerId, state);
    }

    public void createCustomerGroup(CustomerGroupData data) {
        validateCustomerGroup(data);
        customersJdbcRepository.createCustomerGroup(data);
    }

    public CustomerGroupData getCustomerGroup(Long groupId) {
        return customersJdbcRepository.getCustomerGroup(groupId);
    }

    public CustomerGroupResultGetResponse findCustomerGroupsAll(CustomerGroupsFilter filter) {
        List<CustomerGroupGetResult> customerGroups = findCustomerGroups(filter);
        Long count = getAllCustomerGroupsCount();
        return new CustomerGroupResultGetResponse(count, customerGroups);
    }

    public List<CustomerGroupGetResult> findCustomerGroups(CustomerGroupsFilter filter) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = customersJdbcRepository.findCustomerGroups(filter.getSortBy(),
                filter.getPageSize() * (filter.getPage() - 1L), filter.getPageSize(), filter.getOrderBy());
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findCustomerGroups", timeTaken);

        List<CustomerGroupGetResult> list = new ArrayList<>();
        for (Map row : rows) {
            CustomerGroupGetResult data = new CustomerGroupGetResult(
                    Long.valueOf(String.valueOf(row.get("group_id"))),
                    (String) row.get("name"),
                    Long.valueOf(String.valueOf(row.get("number_of_users"))),
                    Long.valueOf(String.valueOf(row.get("status")))
            );
            list.add(data);
        }
        return list;
    }

    public void updateCustomerGroup(CustomerGroupData data) {
        customersJdbcRepository.updateCustomerGroup(data);
    }

    public void deleteCustomerGroup(Long groupId) {
        customersJdbcRepository.updateMoveCustomersToGroup(groupId, UNREGISTERED_CUSTOMER_GROUP_ID);
        customersJdbcRepository.deleteCustomerGroup(groupId);
    }

    public Long getAllCustomerGroupsCount() {
        long startTime = System.currentTimeMillis();
        Long count = customersJdbcRepository.selectAllCustomerGroupsCount();
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "getAllCustomerGroupsCount", timeTaken);
        return count;
    }

    private void validateCustomerGroup(CustomerGroupData data) {
        if (data.getName().equals("")) {
            throw new IllegalArgumentException("Nieprawidłowa nazwa grupy");
        } else if (data.getDiscount() > MAX_DISCOUNT_VALUE || data.getDiscount() < MIN_DISCOUNT_VALUE) {
            throw new IllegalArgumentException("Nieprawidłowa wartość rabatu");
        }
    }


    public void deleteCustomer(Long customerId) {
        customersJdbcRepository.deleteCustomer(customerId);
    }

    public Long getCustomersCount() {
        return customersJdbcRepository.getCustomersCount();
    }

    public List<CustomerData> findCustomers(CustomersFilter filter) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = customersJdbcRepository.findCustomers(filter);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findCustomers", timeTaken);

        List<CustomerData> list = new ArrayList<>();
        for (Map row : rows) {

            list.add(
                    new CustomerData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            String.valueOf(row.get("name")),
                            String.valueOf(row.get("surname")),
                            String.valueOf(row.get("house_number")),
                            String.valueOf(row.get("apartment_number")),
                            String.valueOf(row.get("postal_code")),
                            String.valueOf(row.get("city")),
                            String.valueOf(row.get("email")),
                            String.valueOf(row.get("type")),
                            String.valueOf(row.get("address_line_1")),
                            String.valueOf(row.get("address_line_2")),
                            String.valueOf(row.get("registration_date")),
                            Long.valueOf(String.valueOf(row.get("group_id"))),
                            String.valueOf(row.get("active")),
                            Long.valueOf(String.valueOf(row.get("orders_count")))
                    )
            );
        }

        return list;
    }

    public Long createCustomer(CustomerData data) {
        validateCustomer(data);
        return customersJdbcRepository.createCustomer(data);
    }

    private void validateCustomer(CustomerData data) {
        EmailUtil.validateEmail(data.getEmail());
        if (data.getCity().equals("")) {
            throw new IllegalArgumentException("Miasto nie może być puste!");
        } else if (!isCityInDictionary(data.getCity())) {
            throw new IllegalArgumentException("Miasto " + data.getCity() + " jest nieprawidłowe!");
        }
    }

    private boolean isCityInDictionary(String city) {
        List<DictionaryData> dictionary = dictionariesService.getDictionary(DictionaryType.CITIES, Language.PL);
        for (DictionaryData dictionaryData : dictionary) {
            if(city.toUpperCase().equals(dictionaryData.getValue().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void updateCustomer(CustomerData data) {
        EmailUtil.validateEmail(data.getEmail());
        customersJdbcRepository.updateCustomer(data);
    }
}


