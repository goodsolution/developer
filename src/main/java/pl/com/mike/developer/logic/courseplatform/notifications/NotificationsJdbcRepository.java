package pl.com.mike.developer.logic.courseplatform.notifications;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.NotificationData;
import pl.com.mike.developer.domain.courseplatform.NotificationsFilter;
import pl.com.mike.developer.logic.courseplatform.CourseCustomersService;
import pl.com.mike.developer.logic.courseplatform.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class NotificationsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseCustomersService courseCustomersService;

    public NotificationsJdbcRepository(JdbcTemplate jdbcTemplate, CourseCustomersService courseCustomersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseCustomersService = courseCustomersService;
    }

    void create(NotificationData data) {
        String query = "INSERT INTO crs_notifications (create_datetime, seen_datetime, delete_datetime, customer_id, " +
                "title, content, link, status, type, kind, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getCreateDatetime(), data.getSeenDatetime(), data.getDeleteDatetime(),
                data.getCustomer().getId(), data.getTitle(), data.getContent(), data.getLink(), data.getStatus().getCode(),
                data.getType().getCode(), data.getKind().getCode(), data.getLanguage().getCode());
    }

    List<NotificationData> find(NotificationsFilter filter) {

        List<Object> params = new ArrayList<>();

        String query = "SELECT * FROM crs_notifications";

        boolean filterNotNull = filter != null;

        if(filterNotNull) {

            query += " WHERE 1=1";

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }

            if(filter.getCustomer() != null) {
                query += " AND customer_id = ?";
                params.add(filter.getCustomer().getId());
            }

            if (filter.getType() != null) {
                query += " AND type = ?";
                params.add(filter.getType().getCode());
            }
        }

        query += " ORDER BY create_datetime DESC";

        if(filterNotNull && filter.getLimit() != null) {
            query += " LIMIT ?";
            params.add(filter.getLimit());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, params.toArray());

        List<NotificationData> notifications = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            notifications.add(new NotificationData(
                    getLong(row, "id"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "seen_datetime"),
                    getDateTime(row, "delete_datetime"),
                    prepareCustomer(getLong(row, "customer_id")),
                    getString(row, "title"),
                    getString(row, "content"),
                    getString(row, "link"),
                    NotificationStatus.from(getString(row, "status")),
                    NotificationType.from(getString(row, "type")),
                    NotificationKind.from(getString(row, "kind")),
                    Language.from(getString(row, "language"))

            ));
        }
        return notifications;
    }

    void update(NotificationData data) {

        List<Object> params = new ArrayList<>();
        String query = "UPDATE crs_notifications SET";

        query += " create_datetime = ?,";
        params.add(data.getCreateDatetime());

        query += " seen_datetime = ?,";
        params.add(data.getSeenDatetime());

        query += " delete_datetime = ?,";
        params.add(data.getDeleteDatetime());

        query += " customer_id = ?,";
        params.add(data.getCustomer().getId());

        query += " title = ?,";
        params.add(data.getTitle());

        query += " content = ?,";
        params.add(data.getContent());

        query += " link = ?,";
        params.add(data.getLink());

        query += " status = ?,";
        params.add(data.getStatus().getCode());

        query += " type = ?,";
        params.add(data.getType().getCode());

        query += " kind = ?,";
        params.add(data.getKind().getCode());

        query += " language = ?";
        params.add(data.getLanguage().getCode());

        query += " WHERE id = ?;";
        params.add(data.getId());

        jdbcTemplate.update(query, params.toArray());
    }

    Long unseenNotificationsCount(CustomerData customer) {
        String query = "SELECT count(*) count FROM crs_notifications WHERE seen_datetime IS NULL AND delete_datetime IS NULL AND customer_id = ? AND type = 'p';";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("count"), customer.getId());
    }

    private CustomerData prepareCustomer(Long id) {
        if (id == null) {
            return null;
        } else {
            return courseCustomersService.get(id);
        }
    }
}
