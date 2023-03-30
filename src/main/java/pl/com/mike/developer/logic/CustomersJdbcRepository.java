package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.CustomerData;
import pl.com.mike.developer.domain.CustomerGroupData;
import pl.com.mike.developer.domain.CustomerNewFilter;
import pl.com.mike.developer.domain.CustomersFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
class CustomersJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(CustomersJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    private class CustomerColumnData {
        private String name;
        private Object value;

        public CustomerColumnData(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public CustomersJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    //TODO: if the speed of query execution is not satisfactory,
    // the data of the first order by a given client could be
    // stored in the database and not searched for in each case
    List<Map<String, Object>> findNewCustomers(CustomerNewFilter filter) {
        String query = "" +
                "SELECT ";
        query += findNewCustomersColumns();
        query += "FROM orders AS o1 " +
                "WHERE 1=1 ";
        query = findNewCustomersConditions(query, filter);
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    private String findNewCustomersColumns() {
        return "o1.order_id, " +
                "o1.customer_name, " +
                "o1.customer_surname, " +
                "o1.user_order_id, " +
                "o1.order_basket_sum, " +
                "o1.order_status_id, " +
                "o1.order_datatime ";
    }

    private String findNewCustomersConditions(String query, CustomerNewFilter filter) {
        if (filter.getDateFrom() != null) {
            query += "AND o1.order_date >= '" + filter.getDateFrom() + "' ";
        }
        if (filter.getDateTo() != null) {
            query += "AND o1.order_date <= '" + filter.getDateTo() + "' ";
        }

        query += "AND exists ( " +
                "   select *" +
                "   from orders_products " +
                "   where orders_products.order_id = o1.order_id ";
        if (filter.getCategoryIds() != null && filter.getCategoryIds().length != 0) {
            query += "   AND orders_products.category_id IN (" +
                    Arrays.stream(filter.getCategoryIds()).map(String::valueOf).collect(Collectors.joining(", ")) +
                    "   ) ";
        }
        query += ") ";

        if (filter.getOrderStatusId() != null) {
            query += "AND o1.order_status_id = " + filter.getOrderStatusId() + " ";
        }
        query += "AND o1.order_datatime = (SELECT o2.order_datatime FROM orders AS o2 WHERE o2.logged_buyer_id = o1.logged_buyer_id ORDER BY o2.order_datatime ASC LIMIT 1);";
        return query;
    }

    int updateCustomerDemanding(Long customerId, Boolean state) {
        return jdbcTemplate.update(
                "UPDATE customers SET customer_w = ? WHERE id = ?",
                Boolean.TRUE.equals(state) ? 1 : 0, customerId);
    }

    List<Map<String, Object>> findCustomers(CustomersFilter filter) {
        String query = "SELECT *, (SELECT COUNT(order_id) FROM orders WHERE logged_buyer_id = id) AS orders_count FROM customers";
        if (filter.getWithoutCount()) {
            query = "SELECT *, 0 AS orders_count FROM customers";
        }

        query += " WHERE 1=1";

        if (filter.getNameAndSurname() != null && !filter.getNameAndSurname().isEmpty()) {
            query += " AND CONCAT(TRIM(name), ' ', TRIM(surname)) LIKE '%" + filter.getNameAndSurname() + "%'";
        }

        if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
            query += " AND email LIKE '%" + filter.getEmail() + "%'";
        }

        if (filter.getGroupId() != null) {
            query += " AND group_id = " + filter.getGroupId();
        }

        if (filter.getStatusId() != null) {
            query += " AND active = " + filter.getStatusId();
        }

        if (filter.getSortRecordsBy() != null) {
            query += " ORDER BY " + filter.getSortRecordsBy();

            if(filter.getRecordArrangement() != null) {
                query += " " + filter.getRecordArrangement();
            }
        }

        if (filter.getPage() != null && filter.getPageSize() != null) {
            Long offset = filter.getPageSize() * (filter.getPage() - 1L);
            Long recordsInTable = filter.getPageSize();
            query += " LIMIT " + offset + ", " + recordsInTable;
        }

        return jdbcTemplate.queryForList(query);
    }

    CustomerData getCustomer(Long customerId) {
        String query = "SELECT *, (SELECT COUNT(order_id) FROM orders WHERE logged_buyer_id = id) AS orders_count FROM customers WHERE id = ?";
        //log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{customerId},
                    (rs, rowNum) -> {
                        String city = rs.getString("city");
                        String regulationAccepted = rs.getString("regulation_accepted");
                        String newsletterAccept = rs.getString("newsletter_accept");
                        String customerW  = rs.getString("customer_w");
                        String anotherWeekendAddress = rs.getString("another_weekend_address");
                        String invoice = rs.getString("invoice");
                        String anotherHoursWeekend = rs.getString("another_hours_weekend");
                        CustomerData data = new CustomerData(
                                rs.getLong("id"), // id
                                rs.getString("name"), // firstName
                                rs.getString("surname"), // lastName
                                rs.getString("phone"), //phoneNumber
                                rs.getString("address_line_1"), //street
                                rs.getString("house_number"), //buildingNumber
                                rs.getString("apartment_number"), //apartmentNumber
                                rs.getString("postal_code"), //postalCode
                                city, //city
                                rs.getString("email"), //email
                                rs.getLong("group_id"), //groupId
                                rs.getString("active"), //active
                                rs.getString("registration_date"), //registrationDate
                                rs.getString("registration_ip"), //registrationIp
                                regulationAccepted != null && regulationAccepted.equals("1"), //isRegulationAccepted
                                newsletterAccept != null && newsletterAccept.equals("1"), //isNewsletterAccepted
                                customerW != null && customerW.equals("1"), //demanding
                                anotherWeekendAddress != null && anotherWeekendAddress.equals("1"), //anotherWeekendAddress
                                rs.getString("weekend_street"), //weekendStreet
                                rs.getString("weekend_house_number"), //weekendHouseNo
                                rs.getString("weekend_apartament_number"), //weekendApartmentNo
                                rs.getString("weekend_postal_code"), //weekendPostalCode
                                rs.getString("weekend_city"), //weekendCity
                                invoice != null && invoice.equals("1"), //invoice
                                rs.getString("invoice_company_name"), //invoiceCompanyName
                                rs.getString("invoice_tax_no"), //invoiceTaxNo
                                rs.getString("invoice_street"), //invoiceStreet
                                rs.getString("invoice_house_no"), //invoiceHouseNo
                                rs.getString("invoice_apartment_no"), //invoiceApartmentNo
                                rs.getString("invoice_postal_code"), //invoicePostalCode
                                rs.getString("invoice_city"), //invoiceCity
                                DateTimeUtil.parseHour(rs.getString("week_prefer_hours_to")), //weekPreferredHoursTo
                                anotherHoursWeekend != null && anotherHoursWeekend.equals("1"), //anotherHoursWeekend
                                DateTimeUtil.parseHour(rs.getString("weekend_prefer_hours_to")), //weekendPreferredHoursTo
                                rs.getString("comment"), //comment
                                rs.getString("type"), //type
                                rs.getLong("orders_count"), //ordersCount
                                rs.getString("customer_from") //customerFrom
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void createCustomerGroup(CustomerGroupData data) {
        String query = "INSERT INTO customers_groups (name, discount, status) VALUES (?, ?, ?);";
        jdbcTemplate.update(query, data.getName(), data.getDiscount(), data.getStatus());
    }

    CustomerGroupData getCustomerGroup(Long id) {
        String query = "select * from customers_groups where group_id = ?;";
        return jdbcTemplate.queryForObject(query, new Object[]{id},
                (rs, rowNum) -> new CustomerGroupData(id, rs.getString("name"), rs.getInt("discount"), rs.getInt("status")));
    }

    List<Map<String, Object>> findCustomerGroups(String sortBy, Long offset, Long limit, String orderBy) {
        String query = "SELECT group_id, name, (SELECT COUNT(id) FROM customers WHERE customers.group_id = customers_groups.group_id) AS number_of_users, status " +
                "FROM customers_groups " +
                "ORDER BY " + sortBy + " " + orderBy + " " +
                "LIMIT " + offset + ", " + limit + ";";
        log.trace(query);
        return jdbcTemplate.queryForList(query);
    }

    void updateCustomerGroup(CustomerGroupData data) {
        String query = "update customers_groups set name=?, discount=?, status=? where group_id = ?;";
        jdbcTemplate.update(query, data.getName(), data.getDiscount(), data.getStatus(), data.getId());
    }

    void deleteCustomerGroup(Long groupId) {
        String query = "DELETE FROM customers_groups WHERE group_id = ?;";
        jdbcTemplate.update(query, groupId);
    }

    Long selectAllCustomerGroupsCount() {
        String query = "SELECT COUNT(group_id) AS count FROM customers_groups;";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("count"));
    }

    void updateMoveCustomersToGroup(Long moveFromGroupId, Long moveToGroupId) {
        String query = "UPDATE customers SET group_id = ? WHERE group_id = ?;";
        jdbcTemplate.update(query, moveToGroupId, moveFromGroupId);
    }

    void deleteCustomer(Long customerId) {
        String query = "DELETE FROM customers WHERE id = ?;";
        jdbcTemplate.update(query, customerId);
    }

    Long getCustomersCount() {
        String query = "SELECT COUNT(id) as count FROM customers;";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
    }

    Long createCustomer(CustomerData data) {
        List<CustomerColumnData> customerColumnDataList = prepareColumnsForCustomerCreateQuery(data);
        Object[] params = customerColumnDataList.stream().map(o -> o.getValue()).toArray();

        String insertQuery = "INSERT INTO customers (";
        String valueQuery = "VALUES (";
        int i = 1;
        for (CustomerColumnData customerColumnData : customerColumnDataList) {
            insertQuery += customerColumnData.getName();
            valueQuery += "?";

            if(i == customerColumnDataList.size()) {
                insertQuery += ") ";
                valueQuery += ")";
            } else {
                insertQuery += ", ";
                valueQuery += ", ";
                i++;
            }
        }
        String query = insertQuery + valueQuery;
        jdbcTemplate.update(query, params);
        return commonJdbcRepository.getLastInsertedId();
    }

    private List<CustomerColumnData> prepareColumnsForCustomerCreateQuery(CustomerData data) {
        List<CustomerColumnData> list = new ArrayList<>();
        list.add(new CustomerColumnData("name", data.getFirstName()));
        list.add(new CustomerColumnData("surname", data.getLastName()));
        list.add(new CustomerColumnData("address_line_1", data.getStreet()));
        list.add(new CustomerColumnData("house_number", data.getBuildingNumber()));
        list.add(new CustomerColumnData("apartment_number", data.getApartmentNumber()));
        list.add(new CustomerColumnData("postal_code", data.getPostalCode()));
        list.add(new CustomerColumnData("city", data.getCity()));
        list.add(new CustomerColumnData("email", data.getEmail()));
        list.add(new CustomerColumnData("group_id", data.getGroupId()));
        list.add(new CustomerColumnData("active", data.getActive()));
        list.add(new CustomerColumnData("type", data.getType()));
        list.add(new CustomerColumnData("password", ""));
        list.add(new CustomerColumnData("regulation_accepted", ""));
        list.add(new CustomerColumnData("newsletter_accept", 0));
        list.add(new CustomerColumnData("another_weekend_address", 0));
        list.add(new CustomerColumnData("address_line_2", ""));
        list.add(new CustomerColumnData("country", ""));
        list.add(new CustomerColumnData("company_name", ""));
        list.add(new CustomerColumnData("business_number", ""));
        list.add(new CustomerColumnData("registration_date", ""));
        list.add(new CustomerColumnData("registration_ip", ""));
        list.add(new CustomerColumnData("registration_user_agent", ""));
        list.add(new CustomerColumnData("activation_ip", ""));
        list.add(new CustomerColumnData("activation_date", ""));
        list.add(new CustomerColumnData("shipment_name", ""));
        list.add(new CustomerColumnData("shipment_company_name", ""));
        list.add(new CustomerColumnData("shipment_street", ""));
        list.add(new CustomerColumnData("shipment_house_no", ""));
        list.add(new CustomerColumnData("shipment_apartment_no", ""));
        list.add(new CustomerColumnData("shipment_postal_code", ""));
        list.add(new CustomerColumnData("shipment_city", ""));
        list.add(new CustomerColumnData("invoice", 0));
        list.add(new CustomerColumnData("activation_token", ""));
        list.add(new CustomerColumnData("invoice_company_name", ""));
        list.add(new CustomerColumnData("invoice_tax_no", ""));
        list.add(new CustomerColumnData("invoice_street", ""));
        list.add(new CustomerColumnData("invoice_house_no", ""));
        list.add(new CustomerColumnData("invoice_apartment_no", ""));
        list.add(new CustomerColumnData("invoice_postal_code", ""));
        list.add(new CustomerColumnData("invoice_city", ""));
        list.add(new CustomerColumnData("comment", ""));
        list.add(new CustomerColumnData("another_hours_weekend", 0));
        list.add(new CustomerColumnData("customer_w", 0));
        list.add(new CustomerColumnData("customer_from", data.getCustomerFrom()));
        return list;
    }

    void updateCustomer(CustomerData data) {
        List<CustomerColumnData> customerColumnDataList = prepareColumnsForCustomerUpdateQuery(data);
        Object[] params = customerColumnDataList.stream().map(o -> o.getValue()).toArray();

        String query = "UPDATE customers SET ";
        int i = 1;
        for (CustomerColumnData customerColumnData : customerColumnDataList) {
            query += customerColumnData.getName() + " = ?";

            if(i == customerColumnDataList.size()) {
                query += "  WHERE id = " + data.getId();
            } else {
                query += ", ";
                i++;
            }
        }
        jdbcTemplate.update(query, params);
    }

    private List<CustomerColumnData> prepareColumnsForCustomerUpdateQuery(CustomerData data) {
        List<CustomerColumnData> list = new ArrayList<>();
        list.add(new CustomerColumnData("name", data.getFirstName()));
        list.add(new CustomerColumnData("surname", data.getLastName()));
        list.add(new CustomerColumnData("phone", data.getPhoneNumber()));
        list.add(new CustomerColumnData("address_line_1", data.getStreet()));
        list.add(new CustomerColumnData("house_number", data.getBuildingNumber()));
        list.add(new CustomerColumnData("apartment_number", data.getApartmentNumber()));
        list.add(new CustomerColumnData("postal_code", data.getPostalCode()));
        list.add(new CustomerColumnData("city", data.getCity()));
        list.add(new CustomerColumnData("email", data.getEmail()));
        list.add(new CustomerColumnData("group_id", data.getGroupId()));
        list.add(new CustomerColumnData("active", data.getActive()));
        list.add(new CustomerColumnData("another_weekend_address", (data.getAnotherWeekendAddress()) ? 1 : 0));
        list.add(new CustomerColumnData("weekend_street", data.getWeekendStreet()));
        list.add(new CustomerColumnData("weekend_house_number", data.getWeekendHouseNo()));
        list.add(new CustomerColumnData("weekend_apartament_number", data.getWeekendApartmentNo()));
        list.add(new CustomerColumnData("weekend_postal_code", data.getWeekendPostalCode()));
        list.add(new CustomerColumnData("weekend_city", data.getWeekendCity()));
        list.add(new CustomerColumnData("invoice", (data.getInvoice()) ? 1 : 0));
        list.add(new CustomerColumnData("invoice_company_name", data.getInvoiceCompanyName()));
        list.add(new CustomerColumnData("invoice_tax_no", data.getInvoiceTaxNo()));
        list.add(new CustomerColumnData("invoice_street", data.getInvoiceStreet()));
        list.add(new CustomerColumnData("invoice_house_no", data.getInvoiceHouseNo()));
        list.add(new CustomerColumnData("invoice_apartment_no", data.getInvoiceApartmentNo()));
        list.add(new CustomerColumnData("invoice_postal_code", data.getInvoicePostalCode()));
        list.add(new CustomerColumnData("invoice_city", data.getInvoiceCity()));
        list.add(new CustomerColumnData("week_prefer_hours_to", data.getWeekPreferredHoursTo()));
        list.add(new CustomerColumnData("another_hours_weekend", (data.getAnotherHoursWeekend()) ? 1 : 0));
        list.add(new CustomerColumnData("weekend_prefer_hours_to", data.getWeekendPreferredHoursTo()));
        list.add(new CustomerColumnData("comment", data.getComment()));
        list.add(new CustomerColumnData("customer_w", (data.getDemanding()) ? 1 : 0));
        return list;
    }

}
