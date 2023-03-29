package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class CourseCommentsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CoursesService coursesService;
    private CourseCustomersService courseCustomersService;

    public CourseCommentsJdbcRepository(JdbcTemplate jdbcTemplate, CoursesService coursesService, CourseCustomersService courseCustomersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.coursesService = coursesService;
        this.courseCustomersService = courseCustomersService;
    }

    void create(CourseCommentData data) {
        String query = "INSERT INTO crs_course_comments (course_id, customer_id, text, status, create_datetime) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getCustomer().getId(), data.getText(), data.getStatus(), data.getCreateDatetime());
    }

    List<CourseCommentData> find(CourseCommentsFilter filter) {
        String query = "SELECT * FROM crs_course_comments";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }

            if(filter.getVisibilityStatus() != null) {
                query += " AND status = '" + filter.getVisibilityStatus().getValue() + "'";
            }

            if(filter.getCourseId() != null) {
                query += " AND course_id = " + filter.getCourseId();
            }

            if(filter.getAuthorId() != null) {
                query += " AND course_id IN (SELECT id FROM crs_courses WHERE author_id = " + filter.getAuthorId() + ")";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<CourseCommentData> courseComments = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            courseComments.add(new CourseCommentData(
                    getLong(row, "id"),
                    coursesService.find(new CoursesFilter(getLong(row, "course_id"))).get(0),
                    courseCustomersService.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    getString(row, "text"),
                    getString(row, "status"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "delete_datetime")
            ));
        }
        return courseComments;
    }

    void update(CourseCommentData data) {
        String query = "UPDATE crs_course_comments SET course_id = ?, customer_id = ?, text = ?, status = ?, create_datetime = ?, delete_datetime = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getCustomer().getId(), data.getText(), data.getStatus(), data.getCreateDatetime(), data.getDeleteDatetime(), data.getId());
    }

}
