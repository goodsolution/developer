package pl.com.mike.developer.logic.courseplatform;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class LessonsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CoursesService coursesService;
    private ModulesService modulesService;

    public LessonsJdbcRepository(JdbcTemplate jdbcTemplate, CoursesService coursesService, ModulesService modulesService) {
        this.jdbcTemplate = jdbcTemplate;
        this.coursesService = coursesService;
        this.modulesService = modulesService;
    }

    void create(LessonData data) {
        String query = "INSERT INTO crs_lessons (course_id, title, description, movie_link, order_number, visibility_status, " +
                "module_id, delete_datetime, type, task_solution_description, task_solution_movie_link, movie_link_type, " +
                "task_solution_movie_link_type, create_datetime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getTitle(), data.getDescription(), data.getMovieLink(),
                getMaxOrderNumber() + 1, data.getVisibilityStatus(), prepareModuleId(data.getModule()), data.getDeleteDatetime(),
                data.getType(), data.getTaskSolutionDescription(), data.getTaskSolutionMovieLink(),
                prepareMovieLinkTypeCode(data.getMovieLinkType()), prepareMovieLinkTypeCode(data.getTaskSolutionMovieLinkType()), LocalDateTime.now());
    }

    LessonData get(Long id) {
        try {
            String query = "SELECT * FROM crs_lessons WHERE id = ?;";
            return get(query, new Object[]{id});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson with ID: " + id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new IllegalArgumentException("There is more than one lesson with ID: " + id);
        }
    }

    List<LessonData> find(LessonsFilter filter) {

        String query = "SELECT * FROM crs_lessons";

        if (filter != null) {

            query += " WHERE 1=1";

            if (filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if (filter.getCourseId() != null) {
                query += " AND course_id = " + filter.getCourseId();
            }

            if (filter.getVisibilityStatus() != null) {
                query += " AND visibility_status = '" + filter.getVisibilityStatus().getValue() + "'";
            }

            if (filter.getWithoutModule() != null) {
                if (filter.getWithoutModule()) {
                    query += " AND module_id IS NULL";
                } else {
                    query += " AND module_id IS NOT NULL";
                }
            }

            if (filter.getModuleId() != null) {
                query += " AND module_id = " + filter.getModuleId();
            }

            if(filter.getDeleted() != null) {
                if(filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }

        }

        query += " order by order_number;";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<LessonData> lessons = new ArrayList<>();

        for (Map<String, Object> row : rows) {

            Long moduleId = getLong(row, "module_id");

            ModuleData module;

            if (moduleId == null) {
                module = null;
            } else {
                module = modulesService.get(moduleId);
            }

            lessons.add(new LessonData(
                    getLong(row, "id"),
                    coursesService.find(new CoursesFilter(getLong(row, "course_id"))).get(0),
                    getString(row, "title"),
                    getString(row, "description"),
                    getString(row, "movie_link"),
                    getLong(row, "order_number"),
                    getString(row, "visibility_status"),
                    module,
                    getDateTime(row, "delete_datetime"),
                    getString(row, "type"),
                    getString(row, "task_solution_description"),
                    getString(row, "task_solution_movie_link"),
                    MovieLinkType.from(getString(row, "movie_link_type")),
                    MovieLinkType.from(getString(row, "task_solution_movie_link_type"))
            ));
        }
        return lessons;

    }

    LessonData getWithCustomerActivity(Long lessonId, CustomerData customer) {
        String query = "SELECT *, (SELECT watched FROM crs_customer_lesson_details WHERE customer_id = ? AND lesson_id = ?) AS watched FROM crs_lessons WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{customer.getId(), lessonId, lessonId},
                (rs, rowNum) -> {
                    Long moduleId = getLong(rs, "module_id");
                    ModuleData module;
                    if (moduleId == null) {
                        module = null;
                    } else {
                        module = modulesService.get(moduleId);
                    }
                    return new LessonData(
                            getLong(rs, "id"),
                            coursesService.find(new CoursesFilter(getLong(rs, "course_id"))).get(0),
                            getString(rs, "title"),
                            getString(rs, "description"),
                            getString(rs, "movie_link"),
                            getLong(rs, "order_number"),
                            getBoolean(rs, "watched"),
                            getString(rs, "visibility_status"),
                            module,
                            getDateTime(rs, "delete_datetime"),
                            getString(rs, "type"),
                            getString(rs, "task_solution_description"),
                            getString(rs, "task_solution_movie_link"),
                            MovieLinkType.from(getString(rs, "movie_link_type")),
                            MovieLinkType.from(getString(rs, "task_solution_movie_link_type"))
                    );
                });
    }

    void update(LessonData data) {
        String query = "update crs_lessons set course_id = ?, title = ?, description  = ?, movie_link = ?, order_number = ?, " +
                "visibility_status = ?, module_id = ?, delete_datetime = ?, type = ?, task_solution_description = ?, " +
                "task_solution_movie_link = ?, movie_link_type = ?, task_solution_movie_link_type = ? where id = ?;";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getTitle(), data.getDescription(), data.getMovieLink(),
                data.getOrderNumber(), data.getVisibilityStatus(), prepareModuleId(data.getModule()), data.getDeleteDatetime(),
                data.getType(), data.getTaskSolutionDescription(), data.getTaskSolutionMovieLink(),
                prepareMovieLinkTypeCode(data.getMovieLinkType()), prepareMovieLinkTypeCode(data.getTaskSolutionMovieLinkType()),
                data.getId());
    }

    LessonData getFirstAboveWithoutModule(LessonData data) {
        String query = "select * from crs_lessons where order_number < ? and course_id = ? and module_id is null order by order_number desc limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId()});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson above!");
        }
    }

    LessonData getFirstAboveInModule(LessonData data, Long moduleId) {
        String query = "select * from crs_lessons where order_number < ? and course_id = ? and module_id = ? order by order_number desc limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId(), moduleId});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson above in this module!");
        }
    }

    LessonData getFirstBelowWithoutModule(LessonData data) {
        String query = "select * from crs_lessons where order_number > ? and course_id = ? and module_id is null order by order_number limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId()});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson below!");
        }
    }

    LessonData getFirstBelowInModule(LessonData data, Long moduleId) {
        String query = "select * from crs_lessons where order_number > ? and course_id = ? and module_id = ? order by order_number limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId(), moduleId});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no lesson below in this module!");
        }
    }

    void deleteLessonsInModule(Long moduleId, LocalDateTime deleteDatetime) {
        String query = "UPDATE crs_lessons SET delete_datetime = ? WHERE module_id = ? AND delete_datetime IS NULL;";
        jdbcTemplate.update(query, deleteDatetime, moduleId);
    }

    private LessonData get(String query, Object[] queryArguments) {
        return jdbcTemplate.queryForObject(query, queryArguments,
                (rs, rowNum) -> {

                    Long moduleId = getLong(rs, "module_id");
                    ModuleData module;
                    if (moduleId == null) {
                        module = null;
                    } else {
                        module = modulesService.get(moduleId);
                    }

                    LessonData lesson = new LessonData(
                            getLong(rs, "id"),
                            coursesService.find(new CoursesFilter(getLong(rs, "course_id"))).get(0),
                            getString(rs, "title"),
                            getString(rs, "description"),
                            getString(rs, "movie_link"),
                            getLong(rs, "order_number"),
                            getString(rs, "visibility_status"),
                            module,
                            getDateTime(rs, "delete_datetime"),
                            getString(rs, "type"),
                            getString(rs, "task_solution_description"),
                            getString(rs, "task_solution_movie_link"),
                            MovieLinkType.from(getString(rs, "movie_link_type")),
                            MovieLinkType.from(getString(rs, "task_solution_movie_link_type"))
                    );
                    return lesson;
                });
    }

    private Long getMaxOrderNumber() {
        String query = "select max(order_number) as order_number from crs_lessons;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("order_number"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private Long prepareModuleId(ModuleData module) {
        if (module == null) {
            return null;
        } else {
            return module.getId();
        }
    }

    private String prepareMovieLinkTypeCode(MovieLinkType movieLinkType) {
        if (movieLinkType == null) {
            return null;
        } else {
            return movieLinkType.getCode();
        }
    }
}
