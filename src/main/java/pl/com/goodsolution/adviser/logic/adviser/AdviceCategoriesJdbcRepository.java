package pl.com.goodsolution.adviser.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.*;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class AdviceCategoriesJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AdviceCategoriesJdbcRepository.class);
    private CommonJdbcRepository commonJdbcRepository;

    public AdviceCategoriesJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    List<AdviceCategoryData> find(AdviceCategoriesFilter filter) {

        String query = "SELECT * FROM advice_categories";

        if(filter != null) {

            query += " WHERE 1=1";

            if (filter.getTagsAsSubstring() != null && !filter.getTagsAsSubstring().isEmpty()) {
                query += " AND tags LIKE '%" + filter.getTagsAsSubstring() + "%'";
            }

            if (filter.getSearchAsSubstring() != null && !filter.getSearchAsSubstring().isEmpty()) {
                query += " AND tags LIKE '%" + filter.getSearchAsSubstring() + "%' or " +
                        "description LIKE '%" + filter.getSearchAsSubstring() + "%' ";
            }

            if (filter.getApplicationId() != null) {
                query += " AND application_id = '" + filter.getApplicationId() + "'";
            }

            if (filter.getContext() != null) {
                query += " AND context = '" + filter.getContext() + "'";
            }

            if (filter.getId() != null) {
                query += " AND id = '" + filter.getId() + "'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        log.trace(query);

        return prepareAdviceCategoriesData(jdbcTemplate.queryForList(query));
    }

    List<AdvicePurchasedCategoryData> findPurchasedCategories(AdvicePurchasedCategoriesFilter filter) {

        String query = "SELECT  " +
                "acu.id as id, " +
                "ac.id as category_id, " +
                "ac.name as category_name, " +
                "ac.description as category_description " +
                "FROM advice_categories as ac LEFT JOIN advice_categories_used as acu";

        if(filter != null) {

            query += " ON( ac.id = acu.category_id) WHERE 1=1 ";

            if (filter.getApplicationId() != null) {
                query += " AND ac.application_id = '" + filter.getApplicationId() + "'";
            }

            if (filter.getDomain() != null) {
                query += " AND ac.context = '" + filter.getDomain() + "'";
            }
//
//            if (filter.getDomainId() != null) {
//                query += " AND ac.context_id = '" + filter.getDomainId() + "'";
//            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        log.trace(query);

        return prepareAdvicePurchasedCategoriesData(jdbcTemplate.queryForList(query));
    }

    Long getTotalAdviceCategoriesCount() {
        String query = "SELECT COUNT(id) AS count FROM advice_categories;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> getLong(rs, "count"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private List<AdviceCategoryData> prepareAdviceCategoriesData(List<Map<String, Object>> rows) {
        List<AdviceCategoryData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new AdviceCategoryData(
                    getLong(row, "id"),
                    getString(row, "application_id"),
                    getString(row, "context"),
                    getString(row, "name"),
                    getString(row, "description"),
                    getString(row, "tags"),
                    getBigDecimal(row, "price"),
                    getString(row, "currency_code"),
                    getString(row, "period_code"),
                    getBigDecimal(row, "price_per_month"),
                    getBigDecimal(row, "price_per_quarter"),
                    getBigDecimal(row, "price_per_half_year"),
                    getBigDecimal(row, "price_per_year")));
        }
        return list;
    }
    private List<AdvicePurchasedCategoryData> prepareAdvicePurchasedCategoriesData(List<Map<String, Object>> rows) {
        List<AdvicePurchasedCategoryData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new AdvicePurchasedCategoryData(
                    getLong(row, "id"),//Long id,
                    getString(row, "category_name"),// String name,
                    getString(row, "category_description"),// String description,
                    getLong(row, "category_id")// Long categoryId,
            ));
        }
        return list;
    }

    public Long storePurchase(AdvisePurchaseData data) {
        String query = "INSERT INTO advice_categories_used (" +
               "category_id, customer_id) " +
                "VALUES (?, ?);";
        jdbcTemplate.update(query, data.getCategoryId(), data.getCustomerId());
        return commonJdbcRepository.getLastInsertedId();
    }

    public void removePurchase(Long id) {
        String query = "DELETE FROM advice_categories_used WHERE id = ? ";
        jdbcTemplate.update(query, id);
    }
//    public void updatePurchase(AdvisePurchaseData data) {
//        String query = "UPDATE advice_categories_used SET external_transaction_id = ?, status = ? WHERE id = ?";
//        jdbcTemplate.update(query, data.getExternalTransactionId(), data.getStatus().code(), data.getId());    }

}
