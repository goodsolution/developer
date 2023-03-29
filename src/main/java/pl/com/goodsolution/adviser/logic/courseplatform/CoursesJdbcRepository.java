package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getLong;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class CoursesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;
    private AuthorsService authorsService;

    public CoursesJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository, AuthorsService authorsService) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
        this.authorsService = authorsService;
    }

    public Long create(CourseData data) {
        String query = "INSERT INTO crs_courses (title, description, image_name, price, language, author_id, visibility_status, delete_datetime, code, type, sale_status, create_datetime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getTitle(), data.getDescription(), data.getImageName(), data.getPrice(), data.getLanguage(), data.getAuthor().getId(), data.getVisibilityStatus(), data.getDeleteDatetime(), data.getCode(), data.getType(), data.getSaleStatus(), LocalDateTime.now());
        return commonJdbcRepository.getLastInsertedId();
    }

    CourseData get(Long id) {
        String query = "SELECT * FROM crs_courses WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new Object[]{id},
                (rs, rowNum) -> new CourseData(
                        getLong(rs, "id"),
                        getString(rs, "title"),
                        getString(rs, "description"),
                        getString(rs, "image_name"),
                        getBigDecimal(rs, "price"),
                        getString(rs, "language"),
                        authorsService.find(new AuthorsFilter(getLong(rs, "author_id"))).get(0),
                        getString(rs, "visibility_status"),
                        getDateTime(rs, "delete_datetime"),
                        getString(rs, "code"),
                        getString(rs, "type"),
                        getString(rs, "sale_status")
                ));
    }

    public List<CourseData> find(CoursesFilter filter) {

        String query = "SELECT * FROM crs_courses";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getTitle() != null) {
                query += " AND title LIKE '%" + filter.getTitle() + "%'";
            }

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getAuthorId() != null) {
                query += " AND author_id = " + filter.getAuthorId();
            }

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }

            if(filter.getVisibilityStatus() != null) {
                query += " AND visibility_status = '" + filter.getVisibilityStatus() + "'";
            }

            if(filter.getCode() != null) {
                query += " AND code = '" + filter.getCode() + "'";
            }

            if(filter.getType() != null) {
                query += " AND type = '" + filter.getType().toLowerCase() + "'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        return prepareCourseData(rows);

    }

    public void update(CourseData data) {
        String query = "UPDATE crs_courses SET title = ?, description = ?, image_name = ?, price = ?, language = ?, author_id = ?, visibility_status = ?, delete_datetime = ?, code = ?, type = ?, sale_status = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getTitle(), data.getDescription(), data.getImageName(), data.getPrice(), data.getLanguage(), data.getAuthor().getId(), data.getVisibilityStatus(), data.getDeleteDatetime(), data.getCode(), data.getType(), data.getSaleStatus(), data.getId());
    }

    public List<CourseData> findCoursesOrderedByCustomer(Long customerId, OrderStatus orderStatus, String courseType) {

        String query = "SELECT * FROM crs_courses WHERE ";

        if(courseType != null) {
            query += "type = '" + courseType + "' AND ";
        }

        query += "id IN (SELECT course_id FROM crs_purchased_courses WHERE returned = 0 AND order_id IN (SELECT id FROM crs_orders WHERE customer_id = ? AND status = ?));";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, customerId, orderStatus.getValue());

        return prepareCourseData(rows);
    }

    List<CourseData> findCoursesInOrder(Long orderId) {
        String query = "SELECT * FROM crs_courses WHERE id IN (SELECT course_id FROM crs_purchased_courses WHERE order_id = ?);";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, orderId);
        return prepareCourseData(rows);
    }

    private List<CourseData> prepareCourseData(List<Map<String, Object>> rows) {
        List<CourseData> courses = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            courses.add(new CourseData(
                    getLong(row, "id"),
                    getString(row, "title"),
                    getString(row, "description"),
                    getString(row, "image_name"),
                    getBigDecimal(row, "price"),
                    getString(row, "language"),
                    authorsService.find(new AuthorsFilter(getLong(row, "author_id"))).get(0),
                    getString(row, "visibility_status"),
                    getDateTime(row, "delete_datetime"),
                    getString(row, "code"),
                    getString(row, "type"),
                    getString(row, "sale_status")
            ));
        }
        return courses;
    }
}
