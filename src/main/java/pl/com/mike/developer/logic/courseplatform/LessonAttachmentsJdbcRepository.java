package pl.com.mike.developer.logic.courseplatform;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.FileData;
import pl.com.mike.developer.domain.courseplatform.LessonAttachmentData;
import pl.com.mike.developer.domain.courseplatform.LessonAttachmentsFilter;
import pl.com.mike.developer.domain.courseplatform.LessonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class LessonAttachmentsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private LessonsService lessonsService;
    private FilesService filesService;

    public LessonAttachmentsJdbcRepository(JdbcTemplate jdbcTemplate, LessonsService lessonsService, FilesService filesService) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonsService = lessonsService;
        this.filesService = filesService;
    }

    void create(LessonAttachmentData data) {
        String query = "INSERT INTO crs_lesson_attachments (lesson_id, file_id, name) VALUES (?, ?, ?);";
        jdbcTemplate.update(query, data.getLesson().getId(), data.getFile().getId(), data.getName());
    }

    LessonAttachmentData get(Long id) {
        try {
            String query = "SELECT * FROM crs_lesson_attachments WHERE id = ?;";
            return get(query, new Object[]{id});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson attachment with ID: " + id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new IllegalArgumentException("There is more than one lesson attachment with ID: " + id);
        }
    }

    List<LessonAttachmentData> find(LessonAttachmentsFilter filter) {

        String query = "SELECT * FROM crs_lesson_attachments";

        if (filter != null) {

            query += " WHERE 1=1";

            if (filter.getLessonId() != null) {
                query += " AND lesson_id = " + filter.getLessonId();
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

        List<LessonAttachmentData> lessonAttachments = new ArrayList<>();

        for (Map<String, Object> row : rows) {

            LessonData lesson;
            Long lessonId = getLong(row, "lesson_id");
            if(lessonId == null) {
                lesson = null;
            } else {
                lesson = lessonsService.get(lessonId);
            }

            FileData file;
            Long fileId = getLong(row, "file_id");
            if(fileId == null) {
                file = null;
            } else {
                file = filesService.get(fileId);
            }

            lessonAttachments.add(new LessonAttachmentData(
                    getLong(row, "id"),
                    lesson,
                    file,
                    getString(row, "name"),
                    getDateTime(row, "delete_datetime")
            ));
        }

        return lessonAttachments;

    }

    void update(LessonAttachmentData data) {


        Long lessonId;
        if(data.getLesson() != null) {
            lessonId = data.getLesson().getId();
        } else {
            lessonId = null;
        }

        Long fileId;
        if(data.getFile() != null) {
            fileId = data.getFile().getId();
        } else {
            fileId = null;
        }

        String query = "UPDATE crs_lesson_attachments SET lesson_id = ?, file_id = ?, name = ?, delete_datetime = ? WHERE id = ?";
        jdbcTemplate.update(query, lessonId, fileId, data.getName(), data.getDeleteDatetime(), data.getId());
    }

    private LessonAttachmentData get(String query, Object[] queryArguments) {
        return jdbcTemplate.queryForObject(query, queryArguments,
                (rs, rowNum) -> {

                    LessonData lesson;
                    Long lessonId = getLong(rs, "lesson_id");
                    if(lessonId == null) {
                        lesson = null;
                    } else {
                        lesson = lessonsService.get(lessonId);
                    }

                    FileData file;
                    Long fileId = getLong(rs, "file_id");
                    if(fileId == null) {
                        file = null;
                    } else {
                        file = filesService.get(fileId);
                    }

                    LessonAttachmentData lessonAttachment = new LessonAttachmentData(
                            getLong(rs, "id"),
                            lesson,
                            file,
                            getString(rs, "name"),
                            getDateTime(rs, "delete_datetime")
                    );
                    return lessonAttachment;
                });
    }

}
