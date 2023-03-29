package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.JobOfferData;
import pl.com.goodsolution.adviser.domain.courseplatform.JobOffersFilter;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class JobOffersJdbcRepository {
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public JobOffersJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    List<JobOfferData> find(JobOffersFilter filter) {
        String query = "SELECT * FROM crs_job_offers";
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
        List<JobOfferData> jobOffers = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            jobOffers.add(new JobOfferData(
                    getLong(row, "id"),
                    getString(row, "title"),
                    getString(row, "content"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "delete_datetime")
            ));
        }
        return jobOffers;
    }

    void create(JobOfferData data, Long customerId) {
        String query = "insert into crs_job_offers (create_datetime, delete_datetime, creator_customer_id, title, content) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(query, LocalDate.now(), null, customerId, data.getTitle(), data.getContent());
        commonJdbcRepository.getLastInsertedId();
    }

    void update(JobOfferData data, Long customerId) {
        String query = "update crs_job_offers set create_datetime=?, delete_datetime=?, creator_customer_id=?, title=?, content=? where id=?;";
        jdbcTemplate.update(query, data.getCreateDateTime(), data.getDeleteDatetime(), customerId, data.getTitle(), data.getContent(), data.getId());
    }

}