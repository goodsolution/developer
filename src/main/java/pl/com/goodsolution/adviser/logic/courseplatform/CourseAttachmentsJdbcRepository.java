package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.AuthorData;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseAttachmentData;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseAttachmentsFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.CoursesFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class CourseAttachmentsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CoursesService coursesService;

    public CourseAttachmentsJdbcRepository(JdbcTemplate jdbcTemplate, CoursesService coursesService) {
        this.jdbcTemplate = jdbcTemplate;
        this.coursesService = coursesService;
    }

    void create(CourseAttachmentData data) {
        String query = "INSERT INTO crs_course_attachments (course_id, name, file_name, original_file_name) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getName(), data.getFileName(), data.getOriginalFileName());
    }

    List<CourseAttachmentData> find(CourseAttachmentsFilter filter) {

        List<CourseAttachmentData> attachments = new ArrayList<>();

        String query = "SELECT * FROM crs_course_attachments";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getCourseId() != null) {
                query += " AND course_id = " + filter.getCourseId();
            }

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }

        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> row : rows) {
            attachments.add(new CourseAttachmentData(
                    getLong(row, "id"),
                    coursesService.find(new CoursesFilter(getLong(row, "course_id"))).get(0),
                    getString(row, "name"),
                    getString(row, "file_name"),
                    getDateTime(row, "delete_datetime"),
                    getString(row, "original_file_name")
            ));
        }

        return attachments;
    }

    void update(CourseAttachmentData data) {
        String query = "UPDATE crs_course_attachments SET course_id = ?, name = ?, file_name = ?, delete_datetime = ?, original_file_name = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getName(), data.getFileName(), data.getDeleteDatetime(), data.getOriginalFileName(), data.getId());
    }
}
