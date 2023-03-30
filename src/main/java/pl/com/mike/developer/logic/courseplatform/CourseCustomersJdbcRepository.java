package pl.com.mike.developer.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.AuthorData;
import pl.com.mike.developer.domain.courseplatform.AuthorsFilter;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;
import pl.com.mike.developer.logic.CommonJdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;


@Service
public class CourseCustomersJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;
    private AuthorsJdbcRepository authorsJdbcRepository;

    public CourseCustomersJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository, AuthorsJdbcRepository authorsJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
        this.authorsJdbcRepository = authorsJdbcRepository;
    }

    Long create(CustomerData data) {

        Long authorId;

        if(data.getAuthor() != null) {
            authorId = data.getAuthor().getId();
        } else {
            authorId = null;
        }

        String query = "INSERT INTO crs_customers (login, password_hash, language, regulation_accepted, newsletter_accepted, invoice_type, invoice_first_and_last_name, invoice_company_name, invoice_street, invoice_postal_code, invoice_city, invoice_nip, invoice_country, is_enabled, author_id, registration_datetime, registration_ip, registration_user_agent, email_confirmed) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getLogin(), data.getPasswordHash(), data.getLanguage(), data.getRegulationAccepted(),
                data.getNewsletterAccepted(), data.getInvoiceType(), data.getInvoiceFirstAndLastName(), data.getInvoiceCompanyName(),
                data.getInvoiceStreet(), data.getInvoicePostalCode(), data.getInvoiceCity(), data.getInvoiceNip(), data.getInvoiceCountry(),
                data.getEnabled(), authorId, data.getRegistrationDatetime(), data.getRegistrationIp(), data.getRegistrationUserAgent(), data.getEmailConfirmed());
        return commonJdbcRepository.getLastInsertedId();
    }

    List<CustomerData> find(CustomersFilter filter) {

        String query = "SELECT * FROM crs_customers";

        if(filter != null) {
            query += " WHERE 1=1";

            if(filter.getLogin() != null) {
                query += " AND login = '" + filter.getLogin() + "'";
            }

            if(filter.getLoginLike() != null) {
                query += " and login like '%" + filter.getLoginLike() + "%'";
            }

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if (filter.getEnabled() != null) {
                query += " AND is_enabled = " + (filter.getEnabled() ? "1" : "0");
            }

            if (filter.getNewsletterAccepted() != null) {
                query += " AND newsletter_accepted = " + (filter.getNewsletterAccepted() ? "1" : "0");
            }

            if (filter.getEmailConfirmed() != null) {
                query += " AND email_confirmed = " + (filter.getEmailConfirmed() ? "1" : "0");
            }

            if(filter.getLanguage() != null) {
                query += " AND language = '" + filter.getLanguage().getCode()  + "'";
            }

            if (filter.getLimit() != null) {
                query += " LIMIT " + filter.getLimit();
            }

            if (filter.getInvoiceFirstAndLastName() != null) {
                query += " and invoice_first_and_last_name like '%" + filter.getInvoiceFirstAndLastName() + "%'";
            }

            if(filter.getId()==null){
                query += " AND delete_datetime is null";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        return prepareCustomers(jdbcTemplate.queryForList(query));

    }


    void update(CustomerData data) {

        Long authorId;

        if(data.getAuthor() != null) {
            authorId = data.getAuthor().getId();
        } else {
            authorId = null;
        }


        String query = "update crs_customers set login=?, password_hash=?, language=?, regulation_accepted=?, newsletter_accepted=?, invoice_type=?, invoice_first_and_last_name=?, invoice_company_name=?, invoice_street=?, invoice_postal_code=?, invoice_city=?, invoice_nip=?, invoice_country=?, is_enabled=?, author_id=?, registration_datetime=?, registration_ip=?, registration_user_agent=?, email_confirmed=?, delete_datetime=? where id=?;";
        jdbcTemplate.update(query, data.getLogin(), data.getPasswordHash(), data.getLanguage(), data.getRegulationAccepted(),
                data.getNewsletterAccepted(), data.getInvoiceType(), data.getInvoiceFirstAndLastName(), data.getInvoiceCompanyName(),
                data.getInvoiceStreet(), data.getInvoicePostalCode(), data.getInvoiceCity(), data.getInvoiceNip(), data.getInvoiceCountry(),
                data.getEnabled(), authorId, data.getRegistrationDatetime(), data.getRegistrationIp(), data.getRegistrationUserAgent(), data.getEmailConfirmed(), data.getDeleteDatetime(), data.getId());
    }

    List<CustomerData> findConfirmEmailNotificationRecipients() {
        String query = "SELECT * FROM crs_customers WHERE is_enabled = true AND email_confirmed = false AND NOT EXISTS(SELECT * FROM crs_notifications WHERE customer_id = crs_customers.id AND seen_datetime IS NULL AND kind = 'e');";
        return prepareCustomers(jdbcTemplate.queryForList(query));
    }

    private List<CustomerData> prepareCustomers(List<Map<String, Object>> rows) {

        List<CustomerData> customers = new ArrayList<>();

        for (Map<String, Object> row : rows) {

            Long authorId = getLong(row, "author_id");

            AuthorData author;

            if(authorId == null) {
                author = null;
            } else {
                author = authorsJdbcRepository.find(new AuthorsFilter(authorId)).get(0);
            }

            customers.add(new CustomerData(
                    getLong(row, "id"),
                    getString(row, "login"),
                    getString(row, "password_hash"),
                    getString(row, "language"),
                    getBoolean(row, "regulation_accepted"),
                    getBoolean(row, "newsletter_accepted"),
                    getString(row, "invoice_type"),
                    getString(row, "invoice_first_and_last_name"),
                    getString(row, "invoice_company_name"),
                    getString(row, "invoice_street"),
                    getString(row, "invoice_postal_code"),
                    getString(row, "invoice_city"),
                    getString(row, "invoice_nip"),
                    getString(row, "invoice_country"),
                    getBoolean(row, "is_enabled"),
                    author,
                    getDateTime(row, "registration_datetime"),
                    getString(row, "registration_ip"),
                    getString(row, "registration_user_agent"),
                    getBoolean(row, "email_confirmed")
            ));
        }

        return customers;
    }

}
