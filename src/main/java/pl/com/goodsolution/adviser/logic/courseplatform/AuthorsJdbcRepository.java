package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.AuthorData;
import pl.com.goodsolution.adviser.domain.courseplatform.AuthorsFilter;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class AuthorsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public AuthorsJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    public Long create(AuthorData data) {
        String query = "INSERT INTO crs_authors (first_name, last_name) values (?, ?);";
        jdbcTemplate.update(query, data.getFirstName(), data.getLastName());
        return commonJdbcRepository.getLastInsertedId();
    }

    public List<AuthorData> find(AuthorsFilter filter) {

        List<AuthorData> authors = new ArrayList<>();

        String query = "SELECT * FROM crs_authors";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getFirstName() != null) {
                query += " AND first_name = '" + filter.getFirstName() + "'";
            }

            if(filter.getLastName() != null) {
                query += " AND last_name = '" + filter.getLastName() + "'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> row : rows) {
            authors.add(new AuthorData(
                    getLong(row, "id"),
                    getString(row, "first_name"),
                    getString(row, "last_name")
            ));
        }
        return authors;
    }

    public void update(AuthorData data) {
        String query = "UPDATE crs_authors SET first_name = ?, last_name = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getFirstName(), data.getLastName(), data.getId());
    }

    public void delete(Long id) {
        String query = "DELETE FROM crs_authors WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }
}
