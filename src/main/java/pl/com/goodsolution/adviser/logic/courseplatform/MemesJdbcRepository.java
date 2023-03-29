package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.MemeData;
import pl.com.goodsolution.adviser.domain.courseplatform.MemesFilter;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MemesJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public MemesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void create(MemeData data) {
        String query = "INSERT INTO crs_memes (title, file_name, description, keywords, creation_datetime, language, code) values (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getTitle(), data.getFileName(), data.getDescription(), data.getKeywords(), data.getCreationDatetime(), data.getLanguage(), data.getCode());
    }

    List<MemeData> find(MemesFilter filter) {

        String query = "SELECT * FROM crs_memes";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getCode() != null) {
                query += " AND code = '" + filter.getCode() + "'";
            }
        }

        query += " ORDER BY creation_datetime DESC";

        if(filter != null) {
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<MemeData> memes = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            memes.add(new MemeData(
                    getLong(row, "id"),
                    getString(row, "title"),
                    getString(row, "file_name"),
                    getString(row, "description"),
                    getString(row, "keywords"),
                    getDateTime(row, "creation_datetime"),
                    getString(row, "language"),
                    getString(row, "code")
            ));
        }
        return memes;
    }

    void update(MemeData data) {
        String query = "UPDATE crs_memes SET title = ?, file_name = ?, description = ?, keywords = ?, creation_datetime = ?, language = ?, code = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getTitle(), data.getFileName(), data.getDescription(), data.getKeywords(), data.getCreationDatetime(), data.getLanguage(), data.getCode(), data.getId());
    }

    void delete(Long id) {
        String query = "DELETE FROM crs_memes WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }
}
