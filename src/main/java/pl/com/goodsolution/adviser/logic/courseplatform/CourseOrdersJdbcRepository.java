package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.CustomerGroupData;
import pl.com.goodsolution.adviser.domain.courseplatform.*;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;
import pl.com.goodsolution.adviser.logic.CustomersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class CourseOrdersJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;
    private CourseCustomersService courseCustomersService;

    public CourseOrdersJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository, CourseCustomersService courseCustomersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
        this.courseCustomersService = courseCustomersService;
    }

    Long create(CourseOrderData data) {
        String query = "INSERT INTO crs_orders (number, customer_id, purchase_date, total_price, status, invoice_type, " +
                "invoice_first_and_last_name, invoice_company_name, invoice_street, invoice_postal_code, invoice_city, " +
                "invoice_nip, payu_order_id, payu_payment_url, invoice_country, payu_integration_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getNumber(), data.getCustomer().getId(), data.getPurchaseDate(), data.getTotalPrice(),
                data.getStatus(), data.getInvoiceType(), data.getInvoiceFirstAndLastName(), data.getInvoiceCompanyName(),
                data.getInvoiceStreet(), data.getInvoicePostalCode(), data.getInvoiceCity(), data.getInvoiceNip(),
                data.getPayuOrderId(), data.getPayuPaymentUrl(), data.getInvoiceCountry(), data.getPayuIntegrationStatus());
        return commonJdbcRepository.getLastInsertedId();
    }

    List<CourseOrderData> find(CourseOrdersFilter filter) {
        String query = "SELECT *, (SELECT EXISTS(SELECT * FROM crs_invoices WHERE order_id = crs_orders.id AND delete_date IS NULL)) invoice_issued FROM crs_orders";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getCustomerId() != null) {
                query += " AND customer_id = " + filter.getCustomerId();
            }

            if(filter.getNumber() != null) {
                query += " AND number LIKE '%" + filter.getNumber() + "%'";
            }

            if(filter.getStatus() != null) {
                query += " AND status = '" + filter.getStatus() + "'";
            }

            if(filter.getInvoiceIssued() != null) {
                if(filter.getInvoiceIssued()) {
                    query += " AND (SELECT EXISTS(SELECT * FROM crs_invoices WHERE order_id = crs_orders.id AND delete_date IS NULL)) = true";
                } else {
                    query += " AND (SELECT EXISTS(SELECT * FROM crs_invoices WHERE order_id = crs_orders.id AND delete_date IS NULL)) = false";
                }
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<CourseOrderData> orders = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            orders.add(new CourseOrderData(
                    getLong(row, "id"),
                    getString(row, "number"),
                    courseCustomersService.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    getDateTime(row, "purchase_date"),
                    getBigDecimal(row, "total_price"),
                    getString(row, "status"),
                    getString(row, "invoice_type"),
                    getString(row, "invoice_first_and_last_name"),
                    getString(row, "invoice_company_name"),
                    getString(row, "invoice_street"),
                    getString(row, "invoice_postal_code"),
                    getString(row, "invoice_city"),
                    getString(row, "invoice_nip"),
                    getBoolean(row, "invoice_issued"),
                    getString(row, "payu_order_id"),
                    getString(row, "payu_payment_url"),
                    getString(row, "invoice_country"),
                    getString(row, "payu_integration_status")
            ));
        }
        return orders;
    }

    CourseOrderData getOrderMadeByCustomerWithCourse(CustomerData customer, CourseData course) {

        String query = "SELECT *, (SELECT EXISTS(SELECT * FROM crs_invoices WHERE order_id = crs_orders.id AND delete_date IS NULL)) invoice_issued FROM crs_orders WHERE status = 'p' AND customer_id = ? AND id IN (SELECT order_id FROM crs_purchased_courses WHERE course_id = ? AND returned = 0)";
        return jdbcTemplate.queryForObject(query, new Object[]{customer.getId(), course.getId()},
                (rs, rowNum) -> new CourseOrderData(getLong(rs, "id"),
                        getString(rs, "number"),
                        courseCustomersService.find(new CustomersFilter(getLong(rs, "customer_id"))).get(0),
                        getDateTime(rs, "purchase_date"),
                        getBigDecimal(rs, "total_price"),
                        getString(rs, "status"),
                        getString(rs, "invoice_type"),
                        getString(rs, "invoice_first_and_last_name"),
                        getString(rs, "invoice_company_name"),
                        getString(rs, "invoice_street"),
                        getString(rs, "invoice_postal_code"),
                        getString(rs, "invoice_city"),
                        getString(rs, "invoice_nip"),
                        getBoolean(rs, "invoice_issued"),
                        getString(rs, "payu_order_id"),
                        getString(rs, "payu_payment_url"),
                        getString(rs, "invoice_country"),
                        getString(rs, "payu_integration_status")
                ));

    }

    void update(CourseOrderData data) {
        String query = "UPDATE crs_orders SET number = ?, customer_id = ?, purchase_date = ?, total_price = ?, status = ?, " +
                "invoice_type = ?, invoice_first_and_last_name = ?, invoice_company_name = ?, invoice_street = ?, invoice_postal_code = ?, " +
                "invoice_city = ?, invoice_nip = ?, payu_order_id=?, payu_payment_url=?, invoice_country=?, payu_integration_status=? WHERE id = ?;";
        jdbcTemplate.update(query, data.getNumber(), data.getCustomer().getId(), data.getPurchaseDate(), data.getTotalPrice(),
                data.getStatus(), data.getInvoiceType(), data.getInvoiceFirstAndLastName(), data.getInvoiceCompanyName(),
                data.getInvoiceStreet(), data.getInvoicePostalCode(), data.getInvoiceCity(), data.getInvoiceNip(),
                data.getPayuOrderId(), data.getPayuPaymentUrl(), data.getInvoiceCountry(), data.getPayuIntegrationStatus(), data.getId());
    }
}
