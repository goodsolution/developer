package pl.com.mike.developer.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.InvoiceData;
import pl.com.mike.developer.domain.courseplatform.CourseOrdersFilter;
import pl.com.mike.developer.domain.courseplatform.InvoicesFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class InvoicesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseOrdersService courseOrdersService;

    public InvoicesJdbcRepository(JdbcTemplate jdbcTemplate, CourseOrdersService courseOrdersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseOrdersService = courseOrdersService;
    }

    void create(InvoiceData data) {
        String query = "INSERT INTO crs_invoices (order_id, type, file_name, number, delete_date) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getType(), data.getFileName(), data.getNumber(), data.getDeleteDate());
    }

    List<InvoiceData> find(InvoicesFilter filter) {

        String query = "SELECT * FROM crs_invoices";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getOrderId() != null) {
                query += " AND order_id = " + filter.getOrderId();
            }

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_date IS NOT NULL";
                } else {
                    query += " AND delete_date IS NULL";
                }
            }
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<InvoiceData> invoices = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            invoices.add(new InvoiceData(
                    getLong(row, "id"),
                    courseOrdersService.find(new CourseOrdersFilter(getLong(row, "order_id"), null)).get(0),
                    getString(row, "type"),
                    getString(row, "file_name"),
                    getString(row, "number"),
                    getDateTime(row, "delete_date")
            ));
        }
        return invoices;
    }


    void update(InvoiceData data) {
        String query = "UPDATE crs_invoices SET order_id=?, type=?, file_name=?, number=?, delete_date=? WHERE id=?;";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getType(), data.getFileName(), data.getNumber(), data.getDeleteDate(), data.getId());
    }

    void delete(Long id) {
        String query = "DELETE FROM crs_invoices where id = ?;";
        jdbcTemplate.update(query, id);
    }
}
