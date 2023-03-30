package pl.com.mike.developer.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.PeopleData;
import pl.com.mike.developer.domain.courseplatform.PeopleFilter;
import pl.com.mike.developer.logic.CommonJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.*;

@Service
public class ContactsRepository {
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public ContactsRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    List<PeopleData> find(PeopleFilter filter) {
        String query = "SELECT * FROM people";
        if (filter != null) {
            query += " WHERE 1=1";

            if (filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if (filter.getDeleted() != null) {
                if (filter.getDeleted()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<PeopleData> people = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            people.add(new PeopleData(
                    getLong(row, "id"),
                    getString(row, "first_name"),
                    getString(row, "last_name"),
                    getString(row, "email"),
                    getString(row, "phone"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "delete_datetime"),
                    getDateTime(row, "modify_datetime")
            ));
        }
        return people;
    }

    void create(PeopleData data) {
        String query = "insert into people (first_name ,last_name ,email, phone, create_datetime) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(query,  data.getFirstName(), data.getLastName(), data.getEmail(), data.getPhone(), LocalDate.now());
        commonJdbcRepository.getLastInsertedId();
    }

    void update(PeopleData data) {
        String query = "update people set create_datetime=?, delete_datetime=?, first_name=?, last_name=?, email=?, phone=? where id=?;";
        jdbcTemplate.update(query, data.getCreateDateTime(), data.getDeleteDatetime(), data.getFirstName(), data.getLastName(), data.getEmail(), data.getPhone(), data.getId());
    }

}