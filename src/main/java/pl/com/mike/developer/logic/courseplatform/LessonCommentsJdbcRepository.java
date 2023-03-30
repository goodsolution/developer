package pl.com.mike.developer.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;
import pl.com.mike.developer.domain.courseplatform.LessonCommentData;
import pl.com.mike.developer.domain.courseplatform.LessonCommentsFilter;
import pl.com.mike.developer.domain.courseplatform.LessonsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class LessonCommentsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private LessonsService lessonsService;
    private CourseCustomersService courseCustomersService;

    public LessonCommentsJdbcRepository(JdbcTemplate jdbcTemplate, LessonsService lessonsService, CourseCustomersService courseCustomersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonsService = lessonsService;
        this.courseCustomersService = courseCustomersService;
    }

    void create(LessonCommentData data) {
        String query = "INSERT INTO crs_lesson_comments (lesson_id, customer_id, text, status, create_datetime) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getLesson().getId(), data.getCustomer().getId(), data.getText(), data.getStatus(), data.getCreateDatetime());
    }

    List<LessonCommentData> find (LessonCommentsFilter filter) {
        String query = "SELECT * FROM crs_lesson_comments";

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

            if(filter.getStatus() != null) {
                query += " AND status = '" + filter.getStatus().getValue() + "'";
            }

            if(filter.getLessonId() != null) {
                query += " AND lesson_id = " + filter.getLessonId();
            }

            if(filter.getAuthorId() != null) {
                query += " AND lesson_id IN (SELECT id FROM crs_lessons WHERE course_id IN(SELECT id FROM crs_courses WHERE author_id = " + filter.getAuthorId() + "))";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<LessonCommentData> lessonComments = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            lessonComments.add(new LessonCommentData(
                    getLong(row, "id"),
                    lessonsService.find(new LessonsFilter(getLong(row, "lesson_id"))).get(0),
                    courseCustomersService.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    getString(row, "text"),
                    getString(row, "status"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "delete_datetime")
            ));
        }
        return lessonComments;
    }

    void update(LessonCommentData data) {
        String query = "UPDATE crs_lesson_comments SET lesson_id = ?, customer_id = ?, text = ?, status = ?, create_datetime = ?, delete_datetime = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getLesson().getId(), data.getCustomer().getId(), data.getText(), data.getStatus(), data.getCreateDatetime(), data.getDeleteDatetime(), data.getId());
    }
}
