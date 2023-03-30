package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.auth.AuthenticatedUser;
import pl.com.mike.developer.domain.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
class OrdersJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(OrdersJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;
    private DictionariesService dictionariesService;
    private UsersJdbcRepository usersJdbcRepository;
    private AuthenticatedUser authenticatedUser;
    private CommonJdbcRepository commonJdbcRepository;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S"); //TODO move
    private DateTimeFormatter outTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private class OrderColumnData {
        private String columnName;
        private Object columnValue;

        public OrderColumnData(String columnName, Object columnValue) {
            this.columnName = columnName;
            this.columnValue = columnValue;
        }

        public String getColumnName() {
            return columnName;
        }

        public Object getColumnValue() {
            return columnValue;
        }
    }

    OrdersJdbcRepository(JdbcTemplate jdbcTemplate, DictionariesService dictionariesService, UsersJdbcRepository usersJdbcRepository, AuthenticatedUser authenticatedUser, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.dictionariesService = dictionariesService;
        this.usersJdbcRepository = usersJdbcRepository;
        this.authenticatedUser = authenticatedUser;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    Long create(OrderCreateData data) {
        List<OrderColumnData> list = prepareColumnsForCreateQuery(data);

        Object[] params = list.stream().map(o -> o.getColumnValue()).toArray();

        String query = "INSERT INTO orders ( ";
        String questionMarks = "";
        int i = 1;
        for (OrderColumnData col : list) {
            query += "" + col.getColumnName();
            questionMarks += "?";
            if (i < list.size()) {
                query += ", ";
                questionMarks += ", ";
            }
            i++;
        }
        query += ") VALUES ( ";
        query += questionMarks;
        query += ") ";
        log.trace(query);
        jdbcTemplate.update(
                query,
                params
        );

        return commonJdbcRepository.getLastInsertedId();
    }

    private List<OrderColumnData> prepareColumnsForCreateQuery(OrderCreateData data) {
        List<OrderColumnData> list = new ArrayList<>();
        OrderCustomerData customer = data.getCustomerData();
        OrderInvoiceData invoice = data.getOrderInvoice();
        OrderWeekendAddressData weekendAddress = data.getOrderWeekendAddress();
        DeliveryData deliveryMethod = data.getDeliveryMethod();
        PaymentMethodData paymentMethod = data.getPaymentMethod();

        list.add(new OrderColumnData("customer_name", customer.getFirstName()));
        list.add(new OrderColumnData("customer_surname", customer.getLastName()));
        list.add(new OrderColumnData("customer_type", customer.getType()));
        list.add(new OrderColumnData("customer_phone", customer.getPhoneNumber()));
        list.add(new OrderColumnData("customer_tax_number", null));
        list.add(new OrderColumnData("customer_company_name", null));
        list.add(new OrderColumnData("customer_street", customer.getStreet()));
        list.add(new OrderColumnData("customer_house_number", customer.getBuildingNumber()));
        list.add(new OrderColumnData("customer_apartment_number", customer.getApartmentNumber()));
        list.add(new OrderColumnData("customer_postal_code", customer.getPostalCode()));
        list.add(new OrderColumnData("customer_city", customer.getCityName()));
        list.add(new OrderColumnData("customer_city_id", customer.getCityId()));

        list.add(new OrderColumnData("shipping_data_type", deliveryMethod.getDataType()));
        list.add(new OrderColumnData("shipping_name", deliveryMethod.getFirstName()));
        list.add(new OrderColumnData("shipping_surname", deliveryMethod.getLastName()));
        list.add(new OrderColumnData("shipping_company_name", deliveryMethod.getCompanyName()));
        list.add(new OrderColumnData("shipping_street", deliveryMethod.getStreet()));
        list.add(new OrderColumnData("shipping_house_number", deliveryMethod.getBuildingNumber()));
        list.add(new OrderColumnData("shipping_apartment_number", deliveryMethod.getApartmentNumber()));
        list.add(new OrderColumnData("shipping_postal_code", deliveryMethod.getPostalCode()));
        list.add(new OrderColumnData("shipping_city", deliveryMethod.getCityName()));
        list.add(new OrderColumnData("shipping_city_id", deliveryMethod.getCityId()));

        list.add(new OrderColumnData("shipping_id", deliveryMethod.getId()));
        list.add(new OrderColumnData("shipping_operator_name", deliveryMethod.getOperatorName()));
        list.add(new OrderColumnData("shipping_quantity", deliveryMethod.getQuantity()));
        list.add(new OrderColumnData("shipping_price", deliveryMethod.getPrice()));

        list.add(new OrderColumnData("invoice", invoice.getInvoiceWantedStatus() ? "1" : "0"));
        list.add(new OrderColumnData("invoice_type", invoice.getType()));
        list.add(new OrderColumnData("invoice_name", invoice.getFirstName()));
        list.add(new OrderColumnData("invoice_surname", invoice.getLastName()));
        list.add(new OrderColumnData("invoice_tax_number", invoice.getNip()));
        list.add(new OrderColumnData("invoice_company_name", invoice.getCompanyName()));
        list.add(new OrderColumnData("invoice_street", invoice.getStreet()));
        list.add(new OrderColumnData("invoice_house_number", invoice.getBuildingNumber()));
        list.add(new OrderColumnData("invoice_apartment_number", invoice.getApartmentNumber()));
        list.add(new OrderColumnData("invoice_postal_code", invoice.getPostalCode()));
        list.add(new OrderColumnData("invoice_city", invoice.getCityName()));
        list.add(new OrderColumnData("invoice_city_id", invoice.getCityId()));
        list.add(new OrderColumnData("invoice_status", invoice.isInvoiceIssuedStatus()));

        list.add(new OrderColumnData("order_datatime", data.getPurchaseDate()));
        list.add(new OrderColumnData("order_ip", data.getIp()));
        list.add(new OrderColumnData("order_lang", data.getLang().getCode()));
        list.add(new OrderColumnData("order_curr", data.getCurrency()));
        list.add(new OrderColumnData("order_unique_id", "")); // should be empty

        list.add(new OrderColumnData("order_basket_sum", data.getBasketSum()));
        list.add(new OrderColumnData("order_basket_sum_no", data.getBasketSumNo()));

        list.add(new OrderColumnData("logged_buyer_id", customer.getId()));
        list.add(new OrderColumnData("logged_buyer_email", customer.getEmail()));
        list.add(new OrderColumnData("logged_buyer_type", customer.getType()));
        list.add(new OrderColumnData("logged_buyer_name", customer.getFirstName()));
        list.add(new OrderColumnData("logged_buyer_surname", customer.getLastName()));

        list.add(new OrderColumnData("payment_id", paymentMethod.getId()));
        list.add(new OrderColumnData("payment_company_name", paymentMethod.getName()));
        list.add(new OrderColumnData("payment_operator", paymentMethod.getOperator()));
        list.add(new OrderColumnData("payment_price", paymentMethod.getFee()));
        list.add(new OrderColumnData("payment_status", paymentMethod.getStatus()));
        list.add(new OrderColumnData("payment_update_date", LocalDateTime.now())); //TODO check  in old app

        list.add(new OrderColumnData("order_status_id", data.getOrderStatusId()));
        list.add(new OrderColumnData("comment", data.getComment()));
        list.add(new OrderColumnData("comment_version", "1"));

        list.add(new OrderColumnData("week_hour_of", data.getWeekHourOf()));
        list.add(new OrderColumnData("week_hour_to", data.getWeekHourTo()));

        list.add(new OrderColumnData("another_weekend_hours", data.getAnotherHoursWeekend() ? "1" : "0"));
        list.add(new OrderColumnData("weekend_hour_of", data.getWeekendHourOf()));
        list.add(new OrderColumnData("weekend_hour_to", data.getWeekendHourTo()));

        list.add(new OrderColumnData("another_weekend_address", weekendAddress.getWeekendAddressStatus() ? "1" : "0"));
        list.add(new OrderColumnData("weekend_street", weekendAddress.getStreet()));
        list.add(new OrderColumnData("weekend_house_no", weekendAddress.getBuildingNumber()));
        list.add(new OrderColumnData("weekend_apartament_no", weekendAddress.getApartmentNumber()));
        list.add(new OrderColumnData("weekend_postal_code", weekendAddress.getPostalCode()));
        list.add(new OrderColumnData("weekend_city", weekendAddress.getCityName()));
        list.add(new OrderColumnData("weekend_city_id", weekendAddress.getCityId()));

        list.add(new OrderColumnData("user_order_id", data.getUserOrderId()));
        list.add(new OrderColumnData("table_show", "1"));

        list.add(new OrderColumnData("admin_comments", data.getAdminComment()));
        list.add(new OrderColumnData("driver_comments", ""));

        list.add(new OrderColumnData("receipt", "0"));
        list.add(new OrderColumnData("exception_driver", data.getExceptionDriver() ? "1" : "0"));
        list.add(new OrderColumnData("order_driver_id", data.getDriverId()));
        list.add(new OrderColumnData("group_order", data.getGroup() ? "1" : "0"));
        list.add(new OrderColumnData("buy_from", data.getBuyFrom()));
        list.add(new OrderColumnData("customer_w", "0"));
        list.add(new OrderColumnData("card_payment", "0"));
        list.add(new OrderColumnData("city_discount", data.getCityDiscount()));
        return list;
    }

    public void update(OrderUpdateData data) {
        List<Object> params = new ArrayList<>();
        String query = "update orders " +
                "set ";
        if (data.getDeliveryMethodId() != null) {
            query += "shipping_id = ?, ";
            params.add(data.getDeliveryMethodId());
        }
        if (data.getOrderStatusId() != null) {
            query += "order_status_id = ?, ";
            params.add(data.getOrderStatusId());
        }
        if (data.getPaymentMethodId() != null) {
            query += "payment_id = ?, ";
            params.add(data.getPaymentMethodId());
        }
        if (data.getPaymentStatusId() != null) {
            query += "payment_status = ?, ";
            params.add(data.getPaymentStatusId());
        }
        if (data.getDemandingCustomer() != null) {
            query += "customer_w = ?, ";
            params.add(data.getDemandingCustomer() ? "1" : "0");
        }
        if (data.getReceipt() != null) {
            query += "receipt = ?, ";
            params.add(data.getReceipt() ? "1" : "0");
        }
//        if (data.getEmail() != null) {
//            query += "logged_buyer_email = ?, ";
//            params.add(data.getEmail());
//        }
        if (data.getDriverExclude() != null) {
            query += "exception_driver = ?, ";
            params.add(data.getDriverExclude() ? "1" : "0");
        }
        if (data.getComments() != null) {
            query += "comment = ?, ";
            params.add(data.getComments());
        }
        if (data.getAdminComments() != null) {
            query += "admin_comments = ?, ";
            params.add(data.getAdminComments());
        }
        if (data.getPaymentByCard() != null) {
            query += "card_payment = ?, ";
            params.add(data.getPaymentByCard() ? "1" : "0");
        }

        if (data.getCustomerFirstName() != null) {
            query += "customer_name = ?, ";
            params.add(data.getCustomerFirstName());
        }
        if (data.getCustomerLastName() != null) {
            query += "customer_surname = ?, ";
            params.add(data.getCustomerLastName());
        }
        if (data.getCustomerPhone() != null) {
            query += "customer_phone = ?, ";
            params.add(data.getCustomerPhone());
        }
        if (data.getCustomerStreet() != null) {
            query += "customer_street = ?, ";
            params.add(data.getCustomerStreet());
        }
        if (data.getCustomerBuildingNumber() != null) {
            query += "customer_house_number = ?, ";
            params.add(data.getCustomerBuildingNumber());
        }
        if (data.getCustomerApartmentNumber() != null) {
            query += "customer_apartment_number = ?, ";
            params.add(data.getCustomerApartmentNumber());
        }
        if (data.getCustomerPostalCode() != null) {
            query += "customer_postal_code = ?, ";
            params.add(data.getCustomerPostalCode());
        }
        if (data.getCustomerCityId() != null) {
            query += "customer_city = ?, ";
            params.add(dictionariesService.getDictionaryValueById(data.getCustomerCityId(), DictionaryType.CITIES, Language.PL));
        }

        if (data.getWeekendAddress() != null) {
            query += "another_weekend_address = ?, ";
            params.add(data.getWeekendAddress() ? "1" : "0");
        }
        if (data.getWeekendStreet() != null) {
            query += "weekend_street = ?, ";
            params.add(data.getWeekendStreet());
        }
        if (data.getWeekendBuildingNumber() != null) {
            query += "weekend_house_no = ?, ";
            params.add(data.getWeekendBuildingNumber());
        }
        if (data.getWeekendApartmentNumber() != null) {
            query += "weekend_apartament_no = ?, ";
            params.add(data.getWeekendApartmentNumber());
        }
        if (data.getWeekendPostalCode() != null) {
            query += "weekend_postal_code = ?, ";
            params.add(data.getWeekendPostalCode());
        }
        if (data.getWeekendCityId() != null) {
            query += "weekend_city = ?, ";
            params.add(dictionariesService.getDictionaryValueById(data.getWeekendCityId(), DictionaryType.CITIES, Language.PL));
        }
        if (data.getInvoiceWanted() != null) {
            query += "invoice = ?, ";
            params.add(data.getInvoiceWanted() ? "1" : "0");
        }
        if (data.getInvoiceCompanyName() != null) {
            query += "invoice_company_name = ?, ";
            params.add(data.getInvoiceCompanyName());
        }
        if (data.getInvoiceNip() != null) {
            query += "invoice_tax_number = ?, ";
            params.add(data.getInvoiceNip());
        }
        if (data.getInvoiceStreet() != null) {
            query += "invoice_street = ?, ";
            params.add(data.getInvoiceStreet());
        }
        if (data.getInvoiceBuildingNumber() != null) {
            query += "invoice_house_number = ?, ";
            params.add(data.getInvoiceBuildingNumber());
        }
        if (data.getInvoiceApartmentNumber() != null) {
            query += "invoice_apartment_number = ?, ";
            params.add(data.getInvoiceApartmentNumber());
        }
        if (data.getInvoicePostalCode() != null) {
            query += "invoice_postal_code = ?, ";
            params.add(data.getInvoicePostalCode());
        }
        if (data.getInvoiceCityName() != null) {
            query += "invoice_city = ?, ";
            params.add(data.getInvoiceCityName());
        }
        if (data.getHoursOf() != null) {
            query += "week_hour_of = ?, ";
            params.add(data.getHoursOf());
        }
        if (data.getHoursTo() != null) {
            query += "week_hour_to = ?, ";
            params.add(data.getHoursTo());
        }
        if (data.getWeekendHours() != null) {
            query += "another_weekend_hours = ?, ";
            params.add(data.getWeekendHours() ? "1" : "0");
        }
        if (data.getWeekendHoursTo() != null) {
            query += "weekend_hour_to = ?, ";
            params.add(data.getWeekendHoursTo());
        }
        if (data.getWeekendHoursOf() != null) {
            query += "weekend_hour_of = ?, ";
            params.add(data.getWeekendHoursOf());
        }

        query = query.trim().substring(0, query.length() - 2);

        params.add(data.getId());
        query += " where order_id = ? ";
        log.trace(query);
        jdbcTemplate.update(query,
                params.toArray());
    }

    public void change(OrderUpdateData data) {
        List<Object> params = new ArrayList<>();
        String query = "update orders " +
                "set ";
        if (data.getDeliveryMethodId() != null) {
            query += "shipping_id = ?, ";
            params.add(data.getDeliveryMethodId());
        }
        if (data.getOrderStatusId() != null) {
            query += "order_status_id = ?, ";
            params.add(data.getOrderStatusId());
        }
        if (data.getPaymentMethodId() != null) {
            query += "payment_id = ?, ";
            params.add(data.getPaymentMethodId());
        }
        if (data.getPaymentStatusId() != null) {
            query += "payment_status = ?, ";
            params.add(data.getPaymentStatusId());
        }
        if (data.getDemandingCustomer() != null) {
            query += "customer_w = ?, ";
            params.add(data.getDemandingCustomer() ? "1" : "0");
        }
        if (data.getReceipt() != null) {
            query += "receipt = ?, ";
            params.add(data.getReceipt() ? "1" : "0");
        }
        if (data.getEmail() != null) {
            query += "logged_buyer_email = ?, ";
            params.add(data.getEmail());
        }
        if (data.getDriverExclude() != null) {
            query += "exception_driver = ?, ";
            params.add(data.getDriverExclude() ? "1" : "0");
        }
        if (data.getComments() != null) {
            query += "comment = ?, ";
            params.add(data.getComments());
        }
        if (data.getAdminComments() != null) {
            query += "admin_comments = ?, ";
            params.add(data.getAdminComments());
        }
        if (data.getPaymentByCard() != null) {
            query += "card_payment = ?, ";
            params.add(data.getPaymentByCard() ? "1" : "0");
        }

        if (data.getCustomerFirstName() != null) {
            query += "customer_name = ?, ";
            params.add(data.getCustomerFirstName());
        }
        if (data.getCustomerLastName() != null) {
            query += "customer_surname = ?, ";
            params.add(data.getCustomerLastName());
        }
        if (data.getCustomerPhone() != null) {
            query += "customer_phone = ?, ";
            params.add(data.getCustomerPhone());
        }
        if (data.getCustomerStreet() != null) {
            query += "customer_street = ?, ";
            params.add(data.getCustomerStreet());
        }
        if (data.getCustomerBuildingNumber() != null) {
            query += "customer_house_number = ?, ";
            params.add(data.getCustomerBuildingNumber());
        }
        if (data.getCustomerApartmentNumber() != null) {
            query += "customer_apartment_number = ?, ";
            params.add(data.getCustomerApartmentNumber());
        }
        if (data.getCustomerPostalCode() != null) {
            query += "customer_postal_code = ?, ";
            params.add(data.getCustomerPostalCode());
        }
        if (data.getCustomerCityId() != null) {
            query += "customer_city = ?, ";
            params.add(dictionariesService.getDictionaryValueById(data.getCustomerCityId(), DictionaryType.CITIES, Language.PL));
            query += "customer_city_id = ?, ";
            params.add(data.getCustomerCityId());
        }
        if (data.getWeekendAddress() != null) {
            query += "another_weekend_address = ?, ";
            params.add(data.getWeekendAddress() ? "1" : "0");
        }
        if (data.getWeekendStreet() != null) {
            query += "weekend_street = ?, ";
            params.add(data.getWeekendStreet());
        }
        if (data.getWeekendBuildingNumber() != null) {
            query += "weekend_house_no = ?, ";
            params.add(data.getWeekendBuildingNumber());
        }
        if (data.getWeekendApartmentNumber() != null) {
            query += "weekend_apartament_no = ?, ";
            params.add(data.getWeekendApartmentNumber());
        }
        if (data.getWeekendPostalCode() != null) {
            query += "weekend_postal_code = ?, ";
            params.add(data.getWeekendPostalCode());
        }
        if (data.getWeekendCityId() != null) {
            query += "weekend_city = ?, ";
            params.add(dictionariesService.getDictionaryValueById(data.getWeekendCityId(), DictionaryType.CITIES, Language.PL));
            query += "weekend_city_id = ?, ";
            params.add(data.getWeekendCityId());
        }
        if (data.getInvoice() != null) {
            query += "invoice_status = ?, ";
            params.add(data.getInvoice() ? "1" : "0");
        }
        if (data.getInvoiceWanted() != null) {
            query += "invoice = ?, ";
            params.add(data.getInvoiceWanted() ? "1" : "0");
        }
        if (data.getInvoiceCompanyName() != null) {
            query += "invoice_company_name = ?, ";
            params.add(data.getInvoiceCompanyName());
        }
        if (data.getInvoiceNip() != null) {
            query += "invoice_tax_number = ?, ";
            params.add(data.getInvoiceNip());
        }
        if (data.getInvoiceStreet() != null) {
            query += "invoice_street = ?, ";
            params.add(data.getInvoiceStreet());
        }
        if (data.getInvoiceBuildingNumber() != null) {
            query += "invoice_house_number = ?, ";
            params.add(data.getInvoiceBuildingNumber());
        }
        if (data.getInvoiceApartmentNumber() != null) {
            query += "invoice_apartment_number = ?, ";
            params.add(data.getInvoiceApartmentNumber());
        }
        if (data.getInvoicePostalCode() != null) {
            query += "invoice_postal_code = ?, ";
            params.add(data.getInvoicePostalCode());
        }
        if (data.getInvoiceCityName() != null) {
            query += "invoice_city = ?, ";
            params.add(data.getInvoiceCityName());
            //IMP store city id if exists
        }
        if (data.getHoursOf() != null) {
            query += "week_hour_of = ?, ";
            params.add(data.getHoursOf());
        }
        if (data.getWeekendHoursOf() != null) {
            query += "weekend_hour_of = ?, ";
            params.add(data.getWeekendHoursOf());
        }
        if (data.getHoursTo() != null) {
            query += "week_hour_to = ?, ";
            params.add(data.getHoursTo());
        }
        if (data.getWeekendHours() != null) {
            query += "another_weekend_hours = ?, ";
            params.add(data.getWeekendHours() ? "1" : "0");
        }
        if (data.getWeekendHoursTo() != null) {
            query += "weekend_hour_to = ?, ";
            params.add(data.getWeekendHoursTo());
        }
        if (data.getCommentVersion() != null) {
            query += "comment_version = ?, ";
            params.add(data.getCommentVersion());
        }
        if (data.getGroupOrders() != null) {
            query += "group_order = ?, ";
            params.add(data.getGroupOrders() ? "1" : "0");
        }
        if (data.getCityDiscount() != null) {
            query += "city_discount = ?, ";
            params.add(data.getCityDiscount());
        }
        if (data.getChangeStatusSource() != null) {
            query += "status_change_source = ?, ";
            params.add(data.getChangeStatusSource());
        }

        query = query.trim().substring(0, query.length() - 2);

        params.add(data.getId());
        query += " where order_id = ? ";
        log.trace(query);
        jdbcTemplate.update(query,
                params.toArray());
    }

    OrderDetailsData getOrder(Long id) {
        String query = "SELECT *, " +
                "(select name from orders_statuses os where os.order_status_id = ord.order_status_id limit 1) as order_status_name, " +
                "(select code from discount_codes_usages dcu where dcu.order_id = ord.order_id limit 1) AS discount_code, " +
                "IFNULL((select value from discount_codes_usages dcu, discount_codes dc where dcu.order_id = ord.order_id and dc.code_id = dcu.code_id limit 1), '') AS discount_value, " + //TODO on  form ifnull
                "(select discount_type from discount_codes_usages dcu, discount_codes dc where dcu.order_id = ord.order_id and dc.code_id = dcu.code_id limit 1) AS discount_type," +
                "(select c.customer_w from customers c where id = ord.logged_buyer_id) as customer_customer_w " +
                " FROM orders ord WHERE order_id = ? ";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, new Object[]{id},
                (rs, rowNum) -> {
                    OrderCustomerData orderCustomerData = prepareOrderCustomerData(rs);
                    OrderWeekendAddressData orderWeekendAddressData = prepareOrderWeekendAddressData(rs);
                    OrderInvoiceData orderInvoiceData = prepareOrderInvoiceData(rs);
                    OrderDetailsData data = prepareOrderData(rs, orderCustomerData, orderWeekendAddressData, orderInvoiceData);
                    return data;
                }
        );
    }

    private OrderInvoiceData prepareOrderInvoiceData(ResultSet rs) throws SQLException {
        return new OrderInvoiceData(rs.getInt("invoice") == 1,
                rs.getString("invoice_company_name"),
                rs.getString("invoice_tax_number"),
                rs.getString("invoice_street"),
                rs.getString("invoice_house_number"),
                rs.getString("invoice_apartment_number"),
                rs.getString("invoice_postal_code"),
                3L,
                rs.getString("invoice_city"),
                rs.getInt("invoice_status") == 1,
                dictionariesService.getDictionaryElementByCode(String.valueOf(isInvoiceWanted(rs)), DictionaryType.YES_NO, Language.PL).getValue());
    }

    private OrderWeekendAddressData prepareOrderWeekendAddressData(ResultSet rs) throws SQLException {
        return new OrderWeekendAddressData(
                rs.getInt("another_weekend_address") == 1,
                rs.getString("weekend_street"),
                rs.getString("weekend_house_no"),
                rs.getString("weekend_apartament_no"),
                rs.getString("weekend_postal_code"),
                rs.getString("weekend_city") != null && !"".equals(rs.getString("weekend_city")) ? dictionariesService.getDictionaryIdByValue(rs.getString("weekend_city"), DictionaryType.CITIES, Language.PL) : null, // cityId,
                rs.getString("weekend_city")
        );
    }

    private OrderDetailsData prepareOrderData(ResultSet rs, OrderCustomerData orderCustomerData, OrderWeekendAddressData orderWeekendAddressData, OrderInvoiceData orderInvoiceData) throws SQLException {
        List<Map<String, Object>> cancellations = getCanceledOrdersWithUser(rs.getLong("order_id"));
        // TODO move get to service
        List<OrderCancelledData> list = new ArrayList<>();
        for (Map row : cancellations) {
            OrderCancelledData data = new OrderCancelledData(
                    (String) row.get("reason"), // reason,
                    Long.valueOf(String.valueOf(row.get("changed_by"))),
                    LocalDateTime.parse(String.valueOf(row.get("canceled_at")), dateTimeFormatter),// date
                    new UserData((String) row.get("first_name"), (String) row.get("last_name")));
            list.add(data);
        }

        return new OrderDetailsData(
                rs.getLong("order_id"),
                rs.getString("user_order_id"), //orderId
                rs.getTimestamp("order_datatime").toLocalDateTime(), //orderDate
                rs.getString("order_ip"), //ipAddress
                rs.getString("logged_buyer_email"), //email
                rs.getString("comment"), //comment
                rs.getString("admin_comments"), //adminComment
                rs.getLong("order_status_id"), //statusId
                rs.getString("order_status_name"), //statusName
                isReceipt(rs),//receipt,
                isCustomerDemanding(rs),//demandingCustomer,
                dictionariesService.getDictionaryElementByCode(String.valueOf(isCustomerDemanding(rs)), DictionaryType.YES_NO, Language.PL).getValue(),//demandingCustomerIndicator,
                rs.getLong("payment_id"), //paymentMethodId
                dictionariesService.getDictionaryValueById(rs.getLong("payment_id"), DictionaryType.ORDER_PAYMENT_METHODS, Language.PL), //paymentMethodName
                rs.getInt("card_payment") == 1, //paymentByCard
                rs.getLong("payment_status"), //paymentStatusId
                dictionariesService.getDictionaryValueById(rs.getLong("payment_status"), DictionaryType.PAYMENT_STATUSES, Language.PL), //paymentStatusName
                rs.getLong("shipping_id"), //shipmentTypeId
                rs.getTimestamp("payment_update_date") != null ? rs.getTimestamp("payment_update_date").toLocalDateTime() : null, //paymentStatusUpdateDateTime
                preparePaymentStatusUpdateUser(rs), //paymentStatusUpdateUser
                prepareOrdersGroupEnabled(rs), //groupOrdersEnabled
                rs.getInt("group_order") == 1, //groupOrders
                DateTimeUtil.parseHour(rs.getString("week_hour_to")), //hoursTo
                DateTimeUtil.parseHour(rs.getString("week_hour_of")), //hoursFrom
                rs.getInt("another_weekend_hours") == 1, //weekendHoursStatus
                DateTimeUtil.parseHour(rs.getString("weekend_hour_to")), //weekendHoursTo
                DateTimeUtil.parseHour(rs.getString("weekend_hour_of")), //weekendHoursFrom,
                dictionariesService.getDictionaryElementByCode(String.valueOf(rs.getInt("exception_driver") == 1), DictionaryType.YES_NO, Language.PL).getValue(), //excludeFromDriverSettlement
                orderCustomerData,
                orderWeekendAddressData,
                orderInvoiceData,
                BigDecimal.valueOf(rs.getFloat("order_basket_sum_no")),
                BigDecimal.valueOf(rs.getFloat("order_basket_sum")),
                rs.getString("discount_code"),
                rs.getString("discount_value"), //TODO discount dictionary or cache
                prepareOrderCreationSource(rs),
                list,
                rs.getBigDecimal("city_discount"),
                rs.getInt("exception_driver") == 1,
                rs.getLong("comment_version"),
                rs.getString("status_change_source"),
                rs.getInt("new_calendar") == 1);
    }

    private String preparePaymentStatusUpdateUser(ResultSet rs) throws SQLException {
        OrderPaymentStatusData orderPaymentStatusData = getLastOrderStatusChange(rs.getLong("order_id"));
        if (orderPaymentStatusData != null) {
            UserData user = usersJdbcRepository.getUserById(orderPaymentStatusData.getUserId());
            if (user == null) {
                return "Nieznany/Usunięty";
            }
            return user.getFirstName() + " " + user.getLastName();
        } else {
            return "";
        }
    }

    private String prepareOrderCreationSource(ResultSet rs) throws SQLException {
        String buyFrom = rs.getString("buy_from");
        String ip = rs.getString("order_ip");
        if (buyFrom != null && !buyFrom.isEmpty()) {
            UserData userData = usersJdbcRepository.getUserById(Long.valueOf(buyFrom)); // TODO think about users cache
            return "Zamówione złożone przez panel administracjny przez: " + userData.getFirstName() + " " + userData.getLastName();
        } else {
            if (ip != null && !ip.isEmpty()) {
                return "Zamówienie złożone przez stronę.";
            } else {
                return "Zamówione złożone przez panel administracjny.";
            }
        }
    }

    private Boolean prepareOrdersGroupEnabled(ResultSet rs) throws SQLException {
        int count = getDeliveryOrdersByOrderAndStatusCount(rs.getLong("order_id"), "1");
        if (count > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private OrderCustomerData prepareOrderCustomerData(ResultSet rs) throws SQLException {
        return new OrderCustomerData(
                rs.getLong("logged_buyer_id"),
                rs.getString("customer_name"), //firstName
                rs.getString("customer_surname"), //lastName
                rs.getString("customer_phone"), //phoneNumber
                rs.getString("customer_street"), //street
                rs.getString("customer_house_number"), // buildingNumber
                rs.getString("customer_apartment_number"), // apartmentNumber
                rs.getString("customer_postal_code"), // postalCode
                rs.getString("customer_city") != null && !"".equals(rs.getString("customer_city")) ? dictionariesService.getDictionaryIdByValue(rs.getString("customer_city"), DictionaryType.CITIES, Language.PL) : null, // cityId,
                rs.getString("customer_city"),// cityName,
                rs.getString("logged_buyer_email")
        );
    }

    List<Map<String, Object>> getPaymentsForOrder(Long id) {
        String query = "SELECT " +
                "* " +
                "FROM order_payments WHERE status = 1 AND order_id = " + id;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }


    List<Map<String, Object>> getProductsForOrder(Long id) {
        // SELECT * FROM orders_products WHERE order_id = '".$order_id."' AND is_deleted = 0 ORDER BY ".$sort_column." ".$sort_type.""

        String query = "SELECT " +
                "*, " +
                "(SELECT CONCAT(nazwa, ' ', discount, '%') FROM sale_table st WHERE st.id = sales_use) AS promo," +
                "(select name FROM tax_table tt WHERE tt.tax_id = op.tax_id) AS tax_name," +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name " + //TODO check if left join necessary
                " FROM orders_products op WHERE op.order_id = " + id + " AND op.is_deleted = 0 " +
                "order by order_id desc ";
        log.trace(query);
        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> getProductExtrasForOrder(Long productId) {
        String query = "SELECT * FROM order_extras oe, extras e WHERE oe.extras_id = e.id and oe.order_product_id = " + productId;
        log.trace(query);
        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> getDeliveryDataForOrder(Long id) {
        String query = "SELECT " +
                "*, " +
                "do.id as DeliveryID, " +
                "IF( sunday =1, DATE_ADD( delivery_date, INTERVAL 1 DAY ) , delivery_date ) AS ddate," +
                "(SELECT CONCAT(name, ' ', surname) FROM delivery_driver dd WHERE dd.id = driver_id) AS driver, " +
                "(SELECT categories_languages.name FROM orders_products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.id=do.order_product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name," +
                "(select name from orders_products ops where ops.id = do.order_product_id) as prd_name " +
                "FROM delivery_orders AS do " +
                "LEFT JOIN orders_products AS op " +
                "ON op.order_id = do.order_id " +
                "WHERE do.order_id = ? " +
                "AND do.status NOT IN (-2,  3) " +
                "GROUP BY DeliveryID " +
                "ORDER BY ddate, do.order_product_id ASC ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, id);
    }


    Integer getDeliveryPicturesCount(Long deliveryId) {
        String query = "SELECT count(*) FROM delivery_pictures WHERE delivery_id = " + deliveryId;
        //log.trace(query);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    List<Map<String, Object>> getChangesForOrder(Long id) {
        String query = "SELECT *," +
                "(SELECT CONCAT(name, ' ', surname) FROM users u WHERE u.id = edited_by) as who_edited " +
                "FROM orders_versions WHERE order_id = " + id;
        log.trace(query);
        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> getDeliveryChangesForOrder(Long id) {
        String query = "SELECT *," +
                "(SELECT CONCAT(name, ' ', surname) FROM users u WHERE u.id = edited_by) as who_edited," +
                "(SELECT p.name FROM products p, orders_products op WHERE op.id=dc.order_product_id AND p.product_id=op.product_id) as product_name," +
                "(SELECT op.category_id FROM orders_products op WHERE op.id=dc.order_product_id) as category_id " +
                " FROM delivery_changes dc WHERE order_id = " + id;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    private boolean isCustomerDemanding(ResultSet rs) throws SQLException {
        return rs.getString("customer_w") != null ? rs.getString("customer_w").equals("1") : false;
    }

    private boolean isInvoiceWanted(ResultSet rs) throws SQLException {
        return rs.getString("invoice") != null ? rs.getString("invoice").equals("1") : false;
    }

    private boolean isReceipt(ResultSet rs) throws SQLException {
        return rs.getString("receipt") != null ? rs.getString("receipt").equals("1") : false;
    }

    List<Map<String, Object>> findOrders(OrdersFilter filter,
                                         Long offset, Long limit) {

        if (noOneParamSet(filter)) {
            throw new IllegalArgumentException("Żaden z parametrów wyszukiwania nie jest wybrany");
        }

        String query = "" +
                "select ";
        query += findOrdersColumns();
        query += "from orders ord ";

        if (filter.getPhone() != null) {
            query += "join customers cus on (ord.logged_buyer_id = cus.id) ";
        }
        query +=        "where 1=1 ";
        query = findOrdersConditions(query, filter);
        query += "ORDER BY Order_ID desc ";
        query += "LIMIT " + offset + ", " + limit;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    Long ordersCount(OrdersFilter filter) {
        String query = "" +
                "select count(*)";
        query += "from orders ord ";
        if (filter.getPhone() != null) {
            query += "join customers cus on (ord.logged_buyer_id = cus.id) ";
        }
        query += "where 1=1 ";
        query = findOrdersConditions(query, filter);
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    private boolean noOneParamSet(OrdersFilter filter) {
        return filter.getFirstName() == null && filter.getLastName() == null && filter.getOrderId() == null &&
                filter.getOrderDateFrom() == null && filter.getOrderDateTo() == null && filter.getFirstNameAndLastName() == null &&
                filter.getDeliveryDateFrom() == null && filter.getDeliveryDateTo() == null &&
                filter.getInvoice() == null && filter.getCity() == null && filter.getDiscountCode() == null &&
                filter.getOrderStatusId() == null && filter.getDeliveryMethodId() == null &&
                filter.getDietId() == null && filter.getDriverId() == null && filter.getOrdersFinishingInDays() == null &&
                filter.getMarkedAsPaidDateFrom() == null && filter.getMarkedAsPaidDateTo() == null &&
                filter.getPaymentStatusId() == null
                && filter.getPhone() == null;

    }

    private String findOrdersColumns() {
        return "ord.order_id, " +
                "ord.customer_name, " +
                "ord.customer_surname, " +
                "ord.user_order_id, " +
                "DATEDIFF(" +
                "(" +
                "SELECT MAX(delivery_date) FROM delivery_orders do where do.order_id = ord.order_id " +
                "), '" + LocalDate.now() + "') AS days_left, " +
                "ord.order_datatime, " +
                "ord.order_basket_sum, " +
                "ord.order_status_id, " +
                "ord.shipping_id, " +
                "ord.shipping_operator_name, " +
                "ord.payment_id, " +
                "ord.payment_company_name, " +
                "ord.payment_status, " +
                "ord.receipt, " +
                "ord.invoice, " +
                "ord.invoice_status, " +
                "ord.logged_buyer_id, " +
                "ord.status_change_source, " +
                "(select name from order_payment_statues ops where ops.id = ord.payment_status limit 1) as payment_status_name, " +
                "(select name from orders_statuses os where os.order_status_id = ord.order_status_id limit 1) as order_status_name, " +  //TODO check limit 1
                "(SELECT code FROM discount_codes_usages us WHERE us.order_id = ord.order_id limit 1) as discount_code, " +
                "(SELECT customer_w FROM customers WHERE id = ord.logged_buyer_id limit 1) AS customer_w, " +
                "(SELECT count(*) FROM orders inner_ord WHERE inner_ord.logged_buyer_email = ord.logged_buyer_email AND inner_ord.order_id > ord.order_id AND inner_ord.order_status_id != 6) as next_orders_count " +
                " ";
    }

    private String findOrdersConditions(String query, OrdersFilter filter
    ) {
        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            query += "and ord.customer_name LIKE '%" + filter.getFirstName() + "%' ";
        }
        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            query += "and ord.customer_surname LIKE '%" + filter.getLastName() + "%' ";
        }
        if (filter.getOrderId() != null && !filter.getOrderId().isEmpty()) {
            query += "and ord.user_order_id = '" + filter.getOrderId() + "' ";
        }
        if (filter.getOrderDateFrom() != null) {
            query += "and ord.order_date >= '" + filter.getOrderDateFrom() + "' ";
        }
        if (filter.getOrderDateTo() != null) {
            query += "and ord.order_date <= '" + filter.getOrderDateTo() + "' ";
        }
        if (filter.getFirstNameAndLastName() != null && !filter.getFirstNameAndLastName().isEmpty()) {
            query += "and CONCAT( TRIM(customer_name),  ' ', TRIM(customer_surname) ) LIKE '%" + filter.getFirstNameAndLastName() + "%' ";  //TODO possible performance improvement
        }
        if (filter.getPaymentStatusId() != null) {
            query += "and ord.payment_status = " + filter.getPaymentStatusId() + " ";
        }
        if (filter.getOrderStatusId() != null) {
            if (filter.getOrderStatusId().equals(100L)) {
                query += "and ord.order_status_id IN (1, 5) ";
            } else {
                query += "and ord.order_status_id = " + filter.getOrderStatusId() + " ";
            }
        }
        if (filter.getPaymentMethodId() != null) {
            query += "and ord.payment_id = " + filter.getPaymentMethodId() + " ";
        }
        if (filter.getDiscountCode() != null && !filter.getDiscountCode().isEmpty()) {
            query += "and exists (" +
                    "select * " +
                    "from discount_codes_usages dcu " +
                    "where dcu.order_id = ord.order_id " +
                    "and code LIKE '%" + filter.getDiscountCode() + "%' " +
                    ") ";
        }
        if (filter.getCity() != null && !filter.getCity().isEmpty()) {
            query += "and ord.customer_city = '" + filter.getCity() + "' ";
        }
        if (filter.getDeliveryMethodId() != null) {
            query += "and ord.shipping_id = " + filter.getDeliveryMethodId() + " ";
        }
        if (filter.getInvoice() != null) {
            query += "and ord.invoice = '" + (Boolean.TRUE.equals(filter.getInvoice()) ? "1" : "0") + "' ";
        }
        if (filter.getDriverId() != null) {
            query += "and exists (" +
                    "select * " +
                    "from delivery_orders d " +
                    "where d.order_id = ord.order_id " +
                    "and driver_id = " + filter.getDriverId() + " " +
                    ") ";
        }
        if (filter.getDeliveryDateFrom() != null && filter.getDeliveryDateTo() == null) {
            query += "and exists (" +
                    "select * " +
                    "from delivery_orders d " +
                    "where d.order_id = ord.order_id " +
                    "and delivery_date >= '" + filter.getDeliveryDateFrom() + "' " +
                    ") ";
        }
        if (filter.getDeliveryDateFrom() == null && filter.getDeliveryDateTo() != null) {
            query += "and exists (" +
                    "select * " +
                    "from delivery_orders d " +
                    "where d.order_id = ord.order_id " +
                    "and delivery_date <= '" + filter.getDeliveryDateTo() + "' " +
                    ") ";
        }
        if (filter.getDeliveryDateFrom() != null && filter.getDeliveryDateTo() != null) {
            query += "and exists (" +
                    "select * " +
                    "from delivery_orders d " +
                    "where d.order_id = ord.order_id " +
                    "and delivery_date >= '" + filter.getDeliveryDateFrom() + "' " +
                    "and delivery_date <= '" + filter.getDeliveryDateTo() + "' " +
                    ") ";
        }
        if (filter.getDietId() != null) {
            query += "and exists (" +
                    "select * " +
                    "from orders_products o " +
                    "where o.order_id = ord.order_id " +
                    "and category_id = " + filter.getDietId() + " " +
                    ") ";
        }
        if (filter.getOrdersFinishingInDays() != null) {
            query += " AND DATEDIFF('" + LocalDate.now() + "', " +
                    " (" +
                    " select MAX(delivery_date) " +
                    " from delivery_orders dof " +
                    " where ord.order_id = dof.order_id " +
                    " )" +
                    ") = -" +  filter.getOrdersFinishingInDays() + " ";
        }
        if (filter.getMarkedAsPaidDateFrom() != null) {
            query += "and ord.payment_update_date >= '" + filter.getMarkedAsPaidDateFrom() + "' ";
        }
        if (filter.getMarkedAsPaidDateTo() != null) {
            query += "and ord.payment_update_date < '" + filter.getMarkedAsPaidDateTo().plusDays(1) + "' ";
        }
        if (filter.getPhone() != null) {
            query += "and cus.phone LIKE '%" + filter.getPhone() + "%' ";
        }
        return query;
    }

    Long getAllOrdersCount(LocalDate dateFrom, LocalDate dateTo, Long driverId, Long[] categoryIds, String paymentStatus, Long paymentId, Long shippingId) {


        /*
        function getAllOrdersInFilters($db, $filters) {
	$date_from = ( isset( $filters['date_from'] ) && !empty( $filters['date_from'] ) ) ? $filters['date_from'] : date("Y-m-d");
	$date_to = ( isset( $filters['date_to'] ) && !empty( $filters['date_to'] ) ) ? $filters['date_to'] : date("Y-m-d");
	$filter_query = '';
	if( isset( $filters['shipment'] ) && !empty( $filters['shipment'] ) ) {
		$filter_query .= " AND shipping_id = ".htmlentities( $filters['shipment'] );
	}
	if( isset( $filters['payment_type'] ) && !empty( $filters['payment_type'] ) ) {
		$filter_query .= " AND payment_id = ".htmlentities( $filters['payment_type'] );
	}
	if( isset( $filters['payment_status'] ) && $filters['payment_status'] != "" ) {
		$filter_query .= " AND payment_status = ".htmlentities( $filters['payment_status'] );
	}
	if( isset( $filters['diets'] ) && !empty( $filters['diets'] ) ) {
		$filter_query .= " AND (";
		foreach( $filters['diets'] as $diet ) {
			$filter_query .= " orders_products.category_id = " . $diet . " OR";
		}
		$filter_query = rtrim( $filter_query, 'OR');
		$filter_query .= ")";
	}
	if( isset( $filters['driver'] ) && !empty( $filters['driver'] ) ) {
			$filter_query .= " AND driver_id = ".htmlentities( $filters['driver'] );
	}
	$query = "
		SELECT * FROM orders
		LEFT JOIN orders_products ON orders_products.order_id = orders.order_id
		LEFT JOIN delivery_orders ON delivery_orders.order_product_id = orders_products.id
		WHERE
			DATE(order_datatime) >= '".$date_from."' AND
			DATE(order_datatime) <= '".$date_to."' " .
			$filter_query . " GROUP BY orders.order_id";

	$q = mysqli_query($db, $query);
	$arr = array();
	while ($order = mysqli_fetch_assoc($q)) {
		$arr[] = $order;
	}
	return $arr;
}
         */

        String query = "" +
                "select Count(distinct orders.order_id) as counter " +
                "from orders " +
                "LEFT JOIN orders_products ON orders_products.order_id = orders.order_id " +
                "LEFT JOIN delivery_orders ON delivery_orders.order_product_id = orders_products.id " +
                "where " +
                "DATE(order_datatime) >= '" + dateFrom.toString() + "' and " + //TODO performance improvement possible
                "DATE(order_datatime) <= '" + dateTo.toString() + "' ";

        if (shippingId != null) {
            query += " AND shipping_id = " + shippingId + " ";
        }
        if (paymentId != null) {
            query += " AND payment_id = " + paymentId + " ";
        }
        if (paymentStatus != null) {
            query += " AND payment_status = " + paymentStatus + " ";
        }
        if (driverId != null) {
            query += " AND driver_id = " + driverId + " ";
        }

        // TODO diets
        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> Long.valueOf(rs.getLong("counter"))
        ).get(0);
    }

    List<Map<String, Object>> findOrderSentEmails(Long id) {
        String query = "select " +
                "*, " +
                "IFNULL((SELECT name FROM users u WHERE u.id = send_by), '---') AS sent_by " +
                "from order_mail_sends oms where oms.order_id = " + id;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> getCanceledOrdersWithUser(Long orderId) {
        String query = "SELECT *, " +
                "(SELECT name FROM users WHERE id = changed_by) AS first_name," +
                "(SELECT surname FROM users WHERE id = changed_by) AS last_name " +
                " FROM canceled_orders WHERE order_id = " + orderId + " ORDER BY canceled_at DESC";
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    List<Map<String, Object>> findChangedProducts(Long id) {
        String query = "SELECT *," +
                "(SELECT name FROM products p WHERE p.product_id = opv.product_id) AS product_name " +
                " FROM orders_products_versions opv WHERE version_id = " + id;
        log.trace(query);

        return jdbcTemplate.queryForList(query);
    }

    Integer getDeliveryOrdersByOrderAndStatusCount(Long orderId, String status) {
        String query = "SELECT count(*) FROM delivery_orders WHERE order_id = " + orderId + " AND status = " + status;
        log.trace(query);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    private OrderPaymentStatusData getLastOrderStatusChange(Long orderId) {
        String query = "SELECT * FROM order_payments_changes WHERE order_id = ? ORDER BY datetime DESC LIMIT 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderId},
                    (rs, rowNum) -> {
                        OrderPaymentStatusData data = new OrderPaymentStatusData(
                                rs.getLong("user_id"),
                                rs.getTimestamp("datetime").toLocalDateTime()
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void createPayment(OrderPaymentCreateData data) {
        String query = "" +
                "INSERT INTO order_payments (order_id, value, type, payment_date, added_by, status) " +
                "                    values (?, ?, ?, ?, ?, ?) ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getAmount(),
                data.getPaymentMethodId(),
                LocalDateTime.now(),
                authenticatedUser.getUserId(),
                "1"
        );
    }

    void createDeletedPayment(OrderPaymentDeletedData data) {
        String query = "" +
                "INSERT INTO order_payments_deletes (payment_id, deleted_by, date_time) " +
                "VALUES(?, ?, ?) ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getPaymentId(),
                data.getDeletedBy(),
                data.getDateTime()
        );
    }

    void setPaymentStatus(Long paymentId, String status) {
        String query = "" +
                "UPDATE order_payments SET status = ? WHERE id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                paymentId
        );
    }

    void changeDeliveryHours(OrderDeliveryChangeHoursData data) {
        String query = "" +
                "UPDATE delivery_orders SET dh_from = ?, dh_to = ? WHERE id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getHourFrom(),
                data.getHourTo(),
                data.getDeliveryId()
        );
    }

    void changeDeliveryAddress(OrderDeliveryUpdateData data, Long driverId) {
        String query = "" +
                "UPDATE delivery_orders SET da = 1, da_city = ?, da_street = ?, da_house_no = ?, da_apartament_no = ?, da_postal_code = ?, driver_id = ? WHERE id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                dictionariesService.getDictionaryValueById(data.getCityId(), DictionaryType.CITIES, Language.PL),
                data.getStreet(),
                data.getBuildingNumber(),
                data.getApartmentNumber(),
                data.getPostalCode(),
                driverId,
                data.getDeliveryId()
        );
    }



    void updateDeliveryStatus(Long deliveryId, String status) {
        String query = "UPDATE delivery_orders SET status = ?, delivery_timestamp = ? WHERE id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                LocalDateTime.now(),
                deliveryId
        );
    }

    void updateStatusOfOrder(Long orderId, Long statusId) {
        String query = "UPDATE orders SET order_status_id = ? WHERE order_id =?  ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                statusId,
                orderId
        );
    }

    Long getNextDevliveryToOrderCount(Long orderId) {
        String query = "SELECT count(*) FROM delivery_orders WHERE order_id = ? AND status = 0 ";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, new Object[]{orderId}, Long.class);
    }

    DiscountData getDiscountForOrder(Long orderId) {
        String query = "SELECT * FROM discount_codes_usages WHERE order_id = ? limit 1";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderId},
                    (rs, rowNum) -> {
                        DiscountData data = new DiscountData(
                                rs.getLong("usage_id"),
                                rs.getString("code"),
                                rs.getString("type")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long getDiscountForOrderCount(Long orderId) {
        String query = "SELECT Count(*) FROM discount_codes_usages WHERE order_id =  ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, new Object[]{orderId}, Long.class);
    }

    DiscountData getDiscount(String code) {
        String query = "SELECT * FROM discount_codes WHERE code = ? AND active = 'On' AND (end_date >  ? OR never_end = 1)";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{code, LocalDateTime.now()},
                    (rs, rowNum) -> {
                        DiscountData data = new DiscountData(
                                rs.getLong("code_id"),
                                rs.getString("code"),
                                rs.getString("value"),
                                rs.getString("discount_type"),
                                "discount_code"
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    DiscountData getDiscountValue(String code) {
        String query = "SELECT * FROM discount_codes WHERE code = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{code},
                    (rs, rowNum) -> {
                        DiscountData data = new DiscountData(
                                rs.getLong("code_id"),
                                rs.getString("code"),
                                rs.getString("value"),
                                rs.getString("discount_type"),
                                rs.getString("type")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    DiscountData getVoucher(String code) {
        String query = "SELECT * FROM voucher WHERE voucher = ? AND status = 1 AND (end_date > ? OR never_end = 1)";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{code, LocalDateTime.now()},
                    (rs, rowNum) -> {
                        DiscountData data = new DiscountData(
                                rs.getLong("voucher_id"),
                                rs.getString("code"),
                                rs.getString("value"),
                                rs.getString("discount_type"),
                                "voucher"
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    DiscountData getVoucherValue(String code) {
        String query = "SELECT * FROM voucher WHERE voucher = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{code},
                    (rs, rowNum) -> {
                        DiscountData data = new DiscountData(
                                rs.getLong("voucher_id"),
                                rs.getString("code"),
                                rs.getString("value"),
                                rs.getString("discount_type"),
                                rs.getString("type")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void updateSums(Long orderId, BigDecimal sum, BigDecimal sumWithout) {
        String query = "UPDATE orders SET order_basket_sum = ?, order_basket_sum_no = ? WHERE order_id = ?  ";
        log.trace(query);
        jdbcTemplate.update(
                query,
                sum,
                sumWithout,
                orderId
        );
    }

    void removePartialProductDiscounts(Long orderId) {
        String query = "DELETE FROM order_products_pdiscount WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    void createPartialProductAfterDiscount(Long orderId, Long productId, BigDecimal priceAfterDiscount) {
        String query = "" +
                "INSERT INTO order_products_pdiscount (order_id, order_product_id, discount_price_for_pice) " +
                "VALUES( ?, ?, ? )";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                productId,
                priceAfterDiscount
        );
    }

    void useDiscountCode(OrderDiscountCodeUsageData data) {
        String query = "" +
                "INSERT INTO discount_codes_usages (code, type, code_id, ip, order_id, date_of_usage) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getCode(),
                data.getType(),
                data.getCodeId(),
                data.getIp(),
                data.getOrderId(),
                LocalDateTime.now()
        );
    }

    void updateVoucherStatus(Long voucherId, String status) {
        String query = "UPDATE voucher SET status = ?, use_date = ? WHERE voucher_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                LocalDateTime.now(),
                voucherId
        );
    }

    void removeUsedDiscountCode(Long orderId, Long usageId) {
        String query = "DELETE FROM discount_codes_usages WHERE usage_id = ? AND order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                usageId,
                orderId
        );
    }

    Long getProductsInOrderCount(Long orderId) {
        String query = "SELECT Count(*) FROM orders_products WHERE order_id = ? AND is_deleted = 0";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, new Object[]{orderId}, Long.class);
    }

    void deleteProductFromOrder(Long orderId, Long productId) {
        String query = "UPDATE orders_products SET is_deleted = 1 WHERE id = ? and order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                productId,
                orderId
        );
    }

    void deleteDeliveryOrderForProduct(Long productId) {
        String query = "DELETE FROM delivery_orders WHERE order_product_id = ? AND status = 0 ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                productId
        );
    }

    Long getNumberOfEditOrderCount(Long orderId) {
        String query = "SELECT Count(*) FROM orders_versions WHERE order_id = ?";
        log.trace(query);
        return jdbcTemplate.queryForObject(query, new Object[]{orderId}, Long.class);
    }

    void createOrderProductDeleted(Long orderId, Long productId, Long who, String ip) {
        String query = "" +
                "INSERT INTO orders_products_deleted (order_id, order_product_id, who, deleted_day, ip) " +
                "VALUES(?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                productId,
                who,
                LocalDateTime.now(),
                ip
        );
    }

    Long createOrderVersion(Long orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        log.trace(query);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, orderId);
        for (Map row : rows) {
            query = "INSERT INTO orders_versions (order_id, customer_name, customer_surname, customer_type, customer_phone, customer_tax_number, customer_company_name, customer_street, customer_house_number, customer_apartment_number, customer_postal_code, customer_city, shipping_data_type, shipping_name, shipping_surname, shipping_company_name, shipping_street, shipping_house_number, shipping_apartment_number, shipping_postal_code, shipping_city, invoice, invoice_type, invoice_name, invoice_surname, invoice_tax_number, invoice_company_name, invoice_street, invoice_house_number, invoice_apartment_number, invoice_postal_code, invoice_city, invoice_status, order_datatime, order_ip, order_lang, order_curr, order_unique_id, order_basket_sum, order_basket_sum_no, logged_buyer_id, logged_buyer_email, logged_buyer_type, logged_buyer_name, logged_buyer_surname, shipping_id, shipping_operator_name, shipping_quantity, shipping_price, payment_id, payment_company_name, payment_operator, payment_price, payment_status, payment_update_date, order_status_id, comment, comment_version, week_hour_of, week_hour_to, another_weekend_hours, weekend_hour_of, weekend_hour_to, another_weekend_address, weekend_street, weekend_house_no, weekend_apartament_no, weekend_postal_code, weekend_city, user_order_id, table_show, admin_comments, driver_comments, receipt, exception_driver, order_driver_id, group_order, buy_from, customer_w, card_payment, edited_by, edited_on) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            log.trace(query);
            jdbcTemplate.update(
                    query,
                    orderId,
                    row.get("customer_name"),
                    row.get("customer_surname"),
                    row.get("customer_type"),
                    row.get("customer_phone"),
                    row.get("customer_tax_number"),
                    row.get("customer_company_name"),
                    row.get("customer_street"),

                    row.get("customer_house_number"),
                    row.get("customer_apartment_number"),
                    row.get("customer_postal_code"),
                    row.get("customer_city"),
                    row.get("shipping_data_type"),
                    row.get("shipping_name"),
                    row.get("shipping_surname"),
                    row.get("shipping_company_name"),

                    row.get("shipping_street"),
                    row.get("shipping_house_number"),
                    row.get("shipping_apartment_number"),

                    row.get("shipping_postal_code"),
                    row.get("shipping_city"),
                    row.get("invoice"),
                    row.get("invoice_type"),
                    row.get("invoice_name"),

                    row.get("invoice_surname"),
                    row.get("invoice_tax_number"),
                    row.get("invoice_company_name"),
                    row.get("invoice_street"),
                    row.get("invoice_house_number"),
                    row.get("invoice_apartment_number"),
                    row.get("invoice_postal_code"),
                    row.get("invoice_city"),
                    row.get("invoice_status"),

                    row.get("order_datatime"),
                    row.get("order_ip"),
                    row.get("order_lang"),
                    row.get("order_curr"),
                    row.get("order_unique_id"),
                    row.get("order_basket_sum"),
                    row.get("order_basket_sum_no"),
                    row.get("logged_buyer_id"),

                    row.get("logged_buyer_email"),
                    row.get("logged_buyer_type"),
                    row.get("logged_buyer_name"),
                    row.get("logged_buyer_surname"),
                    row.get("shipping_id"),
                    row.get("shipping_operator_name"),
                    row.get("shipping_quantity"),
                    row.get("shipping_price"),
                    row.get("payment_id"),

                    row.get("payment_company_name"),
                    row.get("payment_operator"),
                    row.get("payment_price"),
                    row.get("payment_status"),
                    row.get("payment_update_date") != null ? row.get("payment_update_date") : null,
                    row.get("order_status_id"),
                    row.get("comment"),
                    row.get("comment_version"),
                    row.get("week_hour_of"),

                    row.get("week_hour_to"),
                    row.get("another_weekend_hours"),
                    row.get("weekend_hour_of"),
                    row.get("weekend_hour_to"),
                    row.get("another_weekend_address"),
                    row.get("weekend_street"),
                    row.get("weekend_house_no"),
                    row.get("weekend_apartament_no"),
                    row.get("weekend_postal_code"),

                    row.get("weekend_city"),
                    row.get("user_order_id"),
                    row.get("table_show"),
                    row.get("admin_comments"),
                    row.get("driver_comments"),
                    row.get("receipt"),
                    row.get("exception_driver"),
                    row.get("order_driver_id"),
                    row.get("group_order"),

                    row.get("buy_from"),
                    row.get("customer_w"),
                    row.get("card_payment"),
                    authenticatedUser.getUserId(),
                    LocalDateTime.now()
            );
        }

        return commonJdbcRepository.getLastInsertedId();
    }



    void createOrderProductVersions(Long orderId, Long versionId) {
        String query = "SELECT * FROM orders_products WHERE order_id = ? AND orders_products.is_deleted = 0";
        log.trace(query);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, orderId);
        for (Map row : rows) {

            query = "" +
                    "INSERT INTO orders_products_versions (" +
                    "order_product_id, " +
                    "order_id, " +
                    "product_id, " +
                    "quantity, " +
                    "size_x, " +
                    "size_y, " +
                    "size_z, " +
                    "weight, " +
                    "category_id, " +
                    "name, " +
                    "tax_id, " +
                    "tax_value, " +
                    "net_price_for_piece, " +
                    "net_pricee, " +
                    "price_for_piece, " +
                    "price, " +
                    "`from`, " +
                    "days, " +
                    "discount_net_price_for_piece, " +
                    "discount_net_pricee, " +
                    "discount_price_for_piece, " +
                    "discount_price, " +
                    "in_weekend, " +
                    "extras, " +
                    "sales_use, " +
                    "order_product_stop, " +
                    "is_test, " +
                    "price_change, " +
                    "version_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            log.trace(query);
            jdbcTemplate.update(
                    query,
                    row.get("id"),
                    orderId,
                    row.get("product_id"),
                    row.get("quantity"),
                    row.get("size_x"),
                    row.get("size_y"),
                    row.get("size_z"),
                    row.get("weight"),
                    row.get("category_id"),
                    row.get("name"),
                    row.get("tax_id"),
                    row.get("tax_value"),
                    row.get("net_price_for_piece"),
                    row.get("net_pricee"),
                    row.get("price_for_piece"),
                    row.get("price"),
                    row.get("from") != null? row.get("from") : null,
                    row.get("days"),
                    row.get("discount_net_price_for_piece"),
                    row.get("discount_net_pricee"),
                    row.get("discount_price_for_piece"),
                    row.get("discount_price"),
                    row.get("in_weekend"),
                    row.get("extras"),
                    row.get("sales_use"),
                    row.get("order_product_stop"),
                    row.get("is_test"),
                    row.get("price_change"),
                    versionId
            );

        }
    }

    void updateOrderProduct(OrderProductModifyData data) {
        String query = "UPDATE orders_products SET " +
                "product_id = ?," +
                "quantity = ?," +
                "category_id = ?," +
                "name = ?, " +
                "tax_id = ?," +
                "tax_value = ?," +
                "net_price_for_piece = ?," +
                "net_pricee = ?," +
                "price_for_piece = ?," +
                "price = ?," +
                "days = ?," +
                "discount_net_price_for_piece = 0," +
                "discount_net_pricee = 0," +
                "discount_price_for_piece = 0," +
                "discount_price = 0," +
                "in_weekend = ?," +
                "extras = ?," +
                "is_test = ?," +
                "price_change = ? " +
                "WHERE id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getProductId(),
                data.getQuantity(),
                data.getDietId(),
                data.getName(),
                data.getTaxId(),
                data.getTaxValue(),
                data.getNetPriceForPiece(),
                data.getNetPricee(),
                data.getPriceForPiece(),
                data.getPrice(),
                data.getDays(),
                data.getWeekendOptionId(),
                data.getExtras() ? "1":"0",
                data.getTest() ? "1":"0",
                data.getPriceChange() != null ? "1" : "0",
                data.getId()

                /*
                                "\t\tproduct_id = '\".$data['product_id'].\"',\n" +
                "\t\tquantity = '\".$data['quantity'].\"',\n" +
                "\t\tcategory_id = '\".$data['category_id'].\"',\n" +
                "\t\tname = '\".$name.\"',
                tax_id = '\".$tax_id.\"',\n" +
                "\t\ttax_value = '\".$tax_value.\"',\n" +
                "\t\tnet_price_for_piece = '\".$net_price_for_piece.\"',\n" +
                "\t\tnet_pricee = '\".$net_pricee.\"',\n" +
                "\t\tprice_for_piece = '\".$price_for_piece.\"',\n" +
                "\t\tprice = '\".$price.\"',\n" +
                "\t\tdays = '\".$data['days'].\"',\n" +
                "\t\tdiscount_net_price_for_piece = '',\n" +
                "\t\tdiscount_net_pricee = '',\n" +
                "\t\tdiscount_price_for_piece = '',\n" +
                "\t\tdiscount_price = '',\n" +
                "\t\tin_weekend = '\".$data['in_weekend'].\"',\n" +
                "\t\textras = '\".$order_extras.\"',\n" +
                "\t\tis_test = '\".$data['is_test'].\"',\n" +
                "\t\tprice_change = '\".$price_change.\"'\n" +
                "\tWHERE id = \" . $order_product_id";
                */

        );
    }

    BigDecimal getDeliveryDiscount(Long deliveryId) {
        String query = "SELECT * FROM delivery_discount WHERE delivery_id =  ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{deliveryId},
                    (rs, rowNum) -> {
                        return rs.getBigDecimal("value");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long createOrderProduct(OrderProductCreateData data, String name, Long taxId, BigDecimal taxValue, BigDecimal netPriceForPiece,
    BigDecimal netPricee, BigDecimal priceForPiece, BigDecimal price, Long days) {
        String query = "" +
                "INSERT INTO orders_products (" +
                "order_id," +
                "product_id," +
                "quantity," +
                "size_x," +
                "size_y," +
                "size_z," +
                "weight," +
                "category_id," +
                "name," +
                "tax_id," +
                "tax_value," +
                "net_price_for_piece," +
                "net_pricee," +
                "price_for_piece," +
                "price," +
                "`from`," +
                "days," +
                "discount_net_price_for_piece," +
                "discount_net_pricee," +
                "discount_price_for_piece," +
                "discount_price," +
                "in_weekend," +
                "extras," +
                "sales_use," +
                "order_product_stop," +
                "is_test," +
                "price_change," +
                "is_deleted" +
                ") VALUES (" +
                "?," +
                "?," +
                "?," +
                "'0'," +
                "'0'," +
                "'0'," +
                "'0'," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "'0'," +
                "'0'," +
                "?," +
                "?," +
                "?" +
                ")";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                        data.getDietTypeId(),
                       data.getQuantity(),
                      data.getDietId(),
                      name,
                taxId,
                taxValue,
                netPriceForPiece,
                netPricee,
                priceForPiece,
                price,
                data.getDateFrom(),
                days,
                0, //null, //"\t\t'\".$discount_net_price_for_piece.\"',\n" +
                0, //null,//"\t\t'\".$discount_net_pricee.\"',\n" +
                0, //null, //"\t\t'\".$discount_price_for_piece.\"',\n" +
                0, //null, //"\t\t'\".$discount_price.\"',\n" +
                data.getWeekendOptionId(), //"\t\t'\".$data['in_weekend'].\"',\n" +
                data.getExtraIds().size() > 0 ? "1" : "0",//"\t\t'\".$order_extras.\"',\n" +
                data.getTestDay() ? "1" : "0",//$data['is_test'].\"'\n" +*/
                "0",
                "0"
        );
        return commonJdbcRepository.getLastInsertedId();
    }

    void createOrderProductExtras(Long orderProductId, Long extrasId) {
        String query = "" +
                "INSERT INTO order_extras (order_product_id, extras_id) VALUES( ?, ? )";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderProductId,
                extrasId
        );
    }

    void deleteOrderProductExtras(Long orderProductId) {
        String query = "" +
                "DELETE FROM order_extras WHERE order_product_id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderProductId
        );
    }

    void createOrderEdit(Long orderId, Long orderProductId, String operation) {
        String query = "" +
                "INSERT INTO orders_edit (order_id, order_product_id, date_time, edited_by, type) VALUES (?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                orderProductId,
                LocalDateTime.now(),
                authenticatedUser.getUserId(),
                operation
        );
    }

    void createDeliveryOrder(OrderDeliveryOrderCreateData data) {
        String query = "" +
                "INSERT INTO delivery_orders (order_id, order_product_id, driver_id, delivery_date, sunday, " +
                "priority, status, delivery_timestamp, day_of_week_value, is_weekend, original_delivery_date) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getOrderId(),
                data.getOrderProductId(),
                data.getDriverId(),
                data.getDeliveryDate(),
                data.getSunday() ? "1" : "0",
                data.getPriority(),
                data.getStatus(),
                data.getTimestamp() != null ? data.getTimestamp() : null, //"0000-00-00 00:00:00",
                data.getDayOfWeekValue(),
                data.getWeekend().booleanValue() == true ? "1" : "0",
                data.getOriginalDeliveryDate()
        );
    }

    void changeOrderProductStopStatus(Long orderProductId, String status) {
        String query = "" +
                "UPDATE orders_products SET order_product_stop = ? WHERE id = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                orderProductId
        );
    }

    void createChangeOrderProductStatus(Long orderProductId, String oldStatus, String newStatus) {
        String query = "" +
                "INSERT INTO `order_products_status_change` (`order_product_id`, `old_status`, `new_status`, `user_id`, `date_time`) VALUES (?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderProductId,
                oldStatus,
                newStatus,
                authenticatedUser.getUserId(),
                LocalDateTime.now()
        );
    }

    OrderProductInfoData getOrderProductInfo(Long orderProductId) {
        String query = "SELECT * FROM orders_products WHERE id =  ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderProductId},
                    (rs, rowNum) -> {
                        return new OrderProductInfoData (
                                rs.getLong("id"),
                                rs.getLong("order_id"),
                                rs.getLong("days"),
                                rs.getLong("quantity"),
                                rs.getLong("in_weekend"),
                                rs.getTimestamp("from").toLocalDateTime().toLocalDate(),
                                rs.getLong("order_product_stop") == 1L
                        );
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void stopDiet(Long orderId, LocalDate date) {
        String query = "" +
                "UPDATE delivery_orders SET status = '-2' WHERE order_id = ? AND delivery_date >= ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                date
        );
    }

    void createCanceledOrder(Long orderId, String reason) {
        String query = "" +
                "INSERT INTO canceled_orders (order_id, reason, changed_by, canceled_at) " +
                "VALUES(?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                reason,
                authenticatedUser.getUserId(),
                LocalDateTime.now()
        );
    }

    List<Map<String, Object>> findOrderProducts(Long orderId) {
        String query = "SELECT * FROM orders_products WHERE order_id = ? ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderId);
    }

    void createOrderStatusChange(Long orderId, Long oldStatusId, Long newStatusId) {
        String query = "" +
                "INSERT INTO `order_status_changes` (`order_id`, `old_status`, `new_status`, `user_id`, `created`) " +
                "VALUES (?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId,
                oldStatusId,
                newStatusId,
                authenticatedUser.getUserId(),
                LocalDateTime.now()
        );
    }

    List<Map<String, Object>> findOrderProductsNotDeleted(Long orderId) {
        String query = "SELECT * FROM orders_products WHERE order_id = ? AND is_deleted = 0 ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderId);
    }

    void deleteOrder(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    void deleteOrderProducts(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    void deleteOrderProductDiscounts(Long orderId) {
        String query = "DELETE FROM order_products_pdiscount WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                orderId
        );
    }

    void changeReceiptStatus(Long orderId, String status) {
        String query = "UPDATE orders SET receipt = ? WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                orderId
        );
    }

    void changeInvoiceStatus(Long orderId, String status) {
        String query = "UPDATE orders SET invoice_status = ? WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                status,
                orderId
        );
    }

    List<Map<String, Object>> getExtras(Long orderProductId) {
        String query = "SELECT * FROM order_extras oe " +
                "JOIN extras e " +
                "ON e.id = oe.extras_id " +
                "WHERE oe.order_product_id = ? ";
        log.trace(query);

        return jdbcTemplate.queryForList(query, orderProductId);
    }

    Long getOrderIdByUserOrderId(String userOrderId) {
        String query = "SELECT * FROM orders WHERE user_order_id =  ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{userOrderId},
                    (rs, rowNum) -> {
                        return rs.getLong("order_id");
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void updateEmail(Long orderId, String email) {
        String query = "UPDATE orders SET logged_buyer_email = ? WHERE order_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                email,
                orderId
        );
    }

    List<Map<String, Object>> findOrders(Long statusId, Long paymentMethodId, LocalDateTime from, LocalDateTime to, Long limit) {
        String query = "SELECT * FROM orders WHERE order_status_id = ? and payment_id = ? " +
                "and order_datatime >= ? and order_datatime <= ? LIMIT ?";
        log.trace(query);

        return jdbcTemplate.queryForList(query, statusId, paymentMethodId, from, to, limit);
    }

    List<Map<String, Object>> findOrderDays(Long orderId) {
        String query = "SELECT odd.id, odd.delivery_date, op.name, odd. quantity, " +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name  " +
                "FROM order_delivery_dates_original odd JOIN orders_products op ON(odd.order_product_id = op.id) " +
                "WHERE odd.order_id = ? " +
                "order by delivery_date, op.product_id ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, orderId);
    }

    List<Map<String, Object>> findOrderDaysHistory(Long orderId) {
        String query = "SELECT odd.id, odd.delivery_date, op.name, odd.status, odd.quantity, odd.delivery_info, " +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name  " +
                "FROM order_delivery_dates odd JOIN orders_products op ON(odd.order_product_id = op.id) " +
                "WHERE odd.order_id = ? and status IN ('" + Const.ORDER_DAY_STATUS_NEW + "', '" + Const.ORDER_DAY_STATUS_SUSPENDED + "', '" + Const.ORDER_DAY_STATUS_REACTIVATED + "', '" + Const.ORDER_DAY_STATUS_FILLED + "', '" + Const.ORDER_DAY_STATUS_RELEASED + "', '" + Const.ORDER_DAY_STATUS_DELIVERED + "', '" + Const.ORDER_DAY_STATUS_ADDED + "') " +
                "order by delivery_date, odd.id, op.product_id ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, orderId);
    }

    List<Map<String, Object>> findOrderDaysForDelivery(Long orderId, Long orderProductId) {
        String query = "SELECT odd.id, odd.delivery_date, op.name, odd.status, odd.quantity, odd.delivery_info,  " +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name  " +
                "FROM order_delivery_dates odd JOIN orders_products op ON(odd.order_product_id = op.id) " +
                "WHERE odd.order_id = ? " +
                "and odd.order_product_id = ? " +
                "and status IN ('" + Const.ORDER_DAY_STATUS_NEW + "', '" + Const.ORDER_DAY_STATUS_REACTIVATED + "', '" + Const.ORDER_DAY_STATUS_FILLED + "', '" + Const.ORDER_DAY_STATUS_DELIVERED + "', '" + Const.ORDER_DAY_STATUS_ADDED + "') " +
                "order by delivery_date, op.product_id  ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, orderId, orderProductId);
    }

    List<Map<String, Object>> findOrderDaysForReactivateDeliveryFrom(Long orderId, Long orderProductId, LocalDate dateFrom) {
        String query = "SELECT odd.id, odd.delivery_date, op.name, odd.status, odd.quantity, odd.delivery_info,  " +
                "(SELECT categories_languages.name FROM products AS pr LEFT JOIN categories_languages ON pr.category_id = categories_languages.category_id WHERE pr.product_id = op.product_id AND categories_languages.language = '" + Language.PL.getCode() + "' ) AS category_name  " +
                "FROM order_delivery_dates odd JOIN orders_products op ON(odd.order_product_id = op.id) " +
                "WHERE odd.order_id = ? and status IN ('" + Const.ORDER_DAY_STATUS_SUSPENDED + "') and delivery_date >= ? and order_product_id = ?" +
                "order by delivery_date ";
        log.trace(query);
        return jdbcTemplate.queryForList(query, orderId, dateFrom, orderProductId);
    }

    void deleteOrderDaysForOrderProduct(Long orderProductId) {
        String query = "UPDATE order_delivery_dates SET status = ? WHERE order_product_id = ? ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                Const.ORDER_DAY_STATUS_REMOVED,
                orderProductId
        );
    }


    void suspendOrderDaysForOrderProductFromDate(Long orderProductId, LocalDate dateFrom) {
        String query = "UPDATE order_delivery_dates SET status = ? WHERE order_product_id = ? and delivery_date >=  ? and status NOT IN ('R', 'S', 'L')";

        log.trace(query);
        jdbcTemplate.update(
                query,
                Const.ORDER_DAY_STATUS_SUSPENDED,
                orderProductId,
                dateFrom
        );
    }

    void startOrderDaysForOrderProductFromDate(Long orderProductId, LocalDate dateFrom, Long orderId) {
        String query = "UPDATE order_delivery_dates SET status = ? WHERE order_product_id = ? and delivery_date >=  ?  and order_id = ? and status = ?";

        log.trace(query);
        jdbcTemplate.update(
                query,
                Const.ORDER_DAY_STATUS_REACTIVATED,
                orderProductId,
                dateFrom,
                orderId,
                Const.ORDER_DAY_STATUS_SUSPENDED
        );
    }

    void releaseOrderDaysForOrderProductForDate(Long orderProductId, LocalDate date, Long orderId) {
        String query = "UPDATE order_delivery_dates SET status = ? WHERE order_product_id = ? and delivery_date =  ? and order_id = ? and status <> ? limit 1 ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                Const.ORDER_DAY_STATUS_RELEASED,
                orderProductId,
                date,
                orderId,
                Const.ORDER_DAY_STATUS_RELEASED
        );
    }

    void setOrderDayAsDeliveredForOrderProductForDate(Long orderProductId, LocalDate date, Long orderId) {
        String query = "UPDATE order_delivery_dates SET delivery_info = ? WHERE order_product_id = ? and delivery_date =  ? and order_id = ? " +
                "and status NOT IN ('R',  'S', 'L', 'D') and delivery_info <> 'D'  limit 1 ";

        log.trace(query);
        jdbcTemplate.update(
                query,
                Const.ORDER_DAY_DELIVERY_INFO_DELIVERED,
                orderProductId,
                date,
                orderId
        );
    }

    void createOrderDay(OrderDaysData data) {
        String query = "" +
                "INSERT INTO `order_delivery_dates` (delivery_date, creation_date, order_product_id, order_id, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getDate(),
                LocalDate.now(),
                data.getOrderProductId(),
                data.getOrderId(),
                data.getStatus()
        );
    }

    void updateOrderDay(OrderDaysData data) {
        String query = "UPDATE order_delivery_dates SET status = ? WHERE order_product_id = ? and delivery_date =  ? and order_id = ? limit 1";

        log.trace(query);
        jdbcTemplate.update(
                query,
                data.getStatus(),
                data.getOrderProductId(),
                data.getDate(),
                data.getOrderId()
        );
    }

    OrderDaysData getOrderDayFor(LocalDate date, Long orderId, Long orderProductId) {
        String query = "SELECT * FROM order_delivery_dates WHERE order_product_id = ? and delivery_date =  ? and order_id = ?";
        log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{orderProductId, date, orderId},
                    (rs, rowNum) -> {
                        return new OrderDaysData (
                                rs.getLong("id")
                        );
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    Long getCustomersLastOrderId(Long customerId) {
        String query = "SELECT order_id FROM orders WHERE logged_buyer_id = ? ORDER BY order_datatime DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{customerId},
                    (rs, rowNum) -> rs.getLong("order_id"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
