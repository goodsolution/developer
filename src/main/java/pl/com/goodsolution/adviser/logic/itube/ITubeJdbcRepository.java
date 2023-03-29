package pl.com.goodsolution.adviser.logic.itube;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.itube.ITubeData;
import pl.com.goodsolution.adviser.domain.itube.ITubeFilter;
import pl.com.goodsolution.adviser.domain.itube.ITubeSearchFilter;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class ITubeJdbcRepository {
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public ITubeJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    List<ITubeData> find(ITubeSearchFilter filter){
        String query = "SELECT * FROM tub_movies ";
        if (filter != null) {
            query += "WHERE 1=1 ";
            if(filter.getPhrase() != null){
                query += "AND (title_pl LIKE '%" + filter.getPhrase() + "%' OR title_en LIKE '%" + filter.getPhrase() + "%' OR keywords LIKE '%" + filter.getPhrase() + "%') ";
            }
            if (filter.isDelete() != null) {
                if (filter.isDelete()) {
                    query += "AND delete_datetime IS NOT NULL ";
                } else {
                    query += "AND delete_datetime IS NULL ";
                }
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        return prepareData(rows);
    }

    List<ITubeData> find(ITubeFilter filter) {
        String query = "SELECT * FROM tub_movies";
        if (filter != null) {
            query += " WHERE 1=1";
            if (filter.getId() != null) {
                query += " AND id = " + "'" + filter.getId() + "'";
            }
            if (filter.getTitlePl() != null) {
                query += " AND title_pl = " + "'" + filter.getTitlePl() + "'";
            }
            if (filter.getTitleEn() != null) {
                query += " AND title_en = " + "'" + filter.getTitleEn() + "'";
            }
            if (filter.getKeywords() != null) {
                query += " AND keywords = " + "'" + filter.getKeywords() + "'";
            }
            if (filter.getLink() != null) {
                query += " AND link = " + "'" + filter.getLink() + "'";
            }
            if (filter.isCreate() != null) {
                if (filter.isCreate()) {
                    query += " AND create_datetime IS NOT NULL";
                } else {
                    query += " AND create_datetime IS NULL";
                }
            }
            if (filter.isDelete() != null) {
                if (filter.isDelete()) {
                    query += " AND delete_datetime IS NOT NULL";
                } else {
                    query += " AND delete_datetime IS NULL";
                }
            }
            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        return prepareData(rows);
    }

    void create(ITubeData data) {
        String query = "INSERT INTO tub_movies (title_pl, title_en, keywords, link, create_datetime, delete_datetime) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(query, data.getTitlePl(), data.getTitleEn(), data.getKeywords(), data.getLink(), data.getCreateDateTime(), data.getDeleteDatetime());
        commonJdbcRepository.getLastInsertedId();
    }

    void update(ITubeData data) {
        String query = "UPDATE tub_movies set title_pl=?, title_en=?, keywords=?, link=?, create_datetime=?, delete_datetime=? where id = ?;";
        jdbcTemplate.update(query, data.getTitlePl(), data.getTitleEn(), data.getKeywords(), data.getLink(), data.getCreateDateTime(), data.getDeleteDatetime(), data.getId());
    }

    private List<ITubeData> prepareData(List<Map<String, Object>> rows) {
        List<ITubeData> iTubes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            iTubes.add(new ITubeData(
                    getLong(row, "id"),
                    getString(row, "title_pl"),
                    getString(row, "title_en"),
                    getString(row, "keywords"),
                    getString(row, "link"),
                    getDateTime(row, "create_datetime"),
                    getDateTime(row, "delete_datetime")
            ));
        }
        return iTubes;
    }


}
