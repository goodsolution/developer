package pl.com.mike.developer.logic.courseplatform;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.ModuleData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class ModulesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CoursesJdbcRepository coursesJdbcRepository;

    public ModulesJdbcRepository(JdbcTemplate jdbcTemplate, CoursesJdbcRepository coursesJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.coursesJdbcRepository = coursesJdbcRepository;
    }

    void create(ModuleData data) {
        String query = "insert into crs_modules (course_id, name, order_number, delete_datetime, visibility_status, create_datetime) values (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getName(), data.getOrderNumber(), data.getDeleteDatetime(), data.getVisibilityStatus(), LocalDateTime.now());
    }

    ModuleData get(Long id) {
        try {
            String query = "SELECT * FROM crs_modules WHERE id = ?;";
            return get(query, new Object[]{id});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no module with ID: " + id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new IllegalArgumentException("There is more than one module with ID: " + id);
        }
    }

    List<ModuleData> find(ModulesFilter filter) {

        String query = "SELECT * FROM crs_modules";

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

            if (filter.getVisibilityStatus() != null) {
                query += " AND visibility_status = '" + filter.getVisibilityStatus().getValue() + "'";
            }

            query += " order by order_number;";

        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<ModuleData> modules = new ArrayList<>();

        for (Map<String, Object> row : rows) {

            modules.add(new ModuleData(
                    getLong(row, "id"),
                    coursesJdbcRepository.get(getLong(row, "course_id")),
                    getString(row, "name"),
                    getLong(row, "order_number"),
                    getDateTime(row, "delete_datetime"),
                    getString(row, "visibility_status")
            ));
        }

        return modules;
    }

    void update(ModuleData data) {
        String query = "update crs_modules set course_id = ?, name = ?, order_number  = ?, delete_datetime = ?, visibility_status = ? where id = ?;";
        jdbcTemplate.update(query, data.getCourse().getId(), data.getName(), data.getOrderNumber(), data.getDeleteDatetime(), data.getVisibilityStatus(), data.getId());
    }


    Long getMaxOrderNumber() {
        String query = "select max(order_number) as order_number from crs_modules;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getLong("order_number"));
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("Can't get max order number");
        }
    }

    ModuleData getFirstAbove(ModuleData data) {
        String query = "select * from crs_modules where order_number < ? and course_id = ? order by order_number desc limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId()});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no module above!");
        }
    }

    ModuleData getFirstBelow(ModuleData data) {
        String query = "select * from crs_modules where order_number > ? and course_id = ? order by order_number limit 1;";
        try {
            return get(query, new Object[]{data.getOrderNumber(), data.getCourse().getId()});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no module below!");
        }
    }

    private ModuleData get(String query, Object[] queryArguments) {
        return jdbcTemplate.queryForObject(query, queryArguments,
                (rs, rowNum) -> {
                    ModuleData module = new ModuleData(
                            getLong(rs, "id"),
                            coursesJdbcRepository.get(getLong(rs, "course_id")),
                            getString(rs, "name"),
                            getLong(rs, "order_number"),
                            getDateTime(rs, "delete_datetime"),
                            getString(rs, "visibility_status")
                    );
                    return module;
                });
    }
}
