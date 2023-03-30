package pl.com.mike.developer.logic.courseplatform;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.FileData;
import pl.com.mike.developer.logic.CommonJdbcRepository;

import static pl.com.mike.developer.logic.adviser.JdbcUtil.getLong;
import static pl.com.mike.developer.logic.adviser.JdbcUtil.getString;

@Service
public class FilesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public FilesJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    Long create(FileData data) {
        String query = "INSERT INTO crs_files (name, original_name, content_type, size_in_bytes) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getName(), data.getOriginalName(), data.getContentType(), data.getSizeInBytes());
        return commonJdbcRepository.getLastInsertedId();
    }

    FileData get(Long id) {
        try {
            String query = "SELECT * FROM crs_files WHERE id = ?;";
            return get(query, new Object[]{id});
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("There is no file with ID: " + id);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new IllegalArgumentException("There is more than one file with ID: " + id);
        }
    }

    private FileData get(String query, Object[] queryArguments) {
        return jdbcTemplate.queryForObject(query, queryArguments,
                (rs, rowNum) -> {
                    FileData file = new FileData(
                            getLong(rs, "id"),
                            getString(rs, "name"),
                            getString(rs, "original_name"),
                            getString(rs, "content_type"),
                            getLong(rs, "size_in_bytes")
                    );
                    return file;
                });
    }

}
