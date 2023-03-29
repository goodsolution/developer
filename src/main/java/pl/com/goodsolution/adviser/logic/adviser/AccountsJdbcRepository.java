package pl.com.goodsolution.adviser.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.AccountData;
import pl.com.goodsolution.adviser.domain.adviser.AccountType;
import pl.com.goodsolution.adviser.domain.adviser.AccountsFilter;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountsJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AccountsJdbcRepository.class);
    private CommonJdbcRepository commonJdbcRepository;

    public AccountsJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    Long create(AccountData data) {
        String query = "INSERT INTO accounts (name, preferred_hour_from, preferred_hour_to, all_day, type, newsletter_accepted, regulations_accepted) value (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, data.getName(), data.getPreferredHourFrom(), data.getPreferredHourTo(), data.getAllDay(), data.getType().code(), data.getNewsletterAccepted() ? 1 : 0, data.getRegulationsAccepted() ? 1 : 0);
        return commonJdbcRepository.getLastInsertedId();
    }

    AccountData get(Long id) {
        String query = "SELECT * FROM accounts WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> new AccountData(
                            getLong(rs, "id"),
                            getInteger(rs, "preferred_hour_from"),
                            getInteger(rs, "preferred_hour_to"),
                            getBoolean(rs, "all_day"),
                            getBoolean(rs, "newsletter_accepted"),
                            getString(rs, "name"),
                            AccountType.fromCode(getString(rs, "type"))
                    ));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    List<AccountData> find(AccountsFilter filter) {

        String query = "SELECT * FROM accounts";

        if(filter != null) {

            query += " WHERE 1=1";

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                query += " AND name LIKE '%" + filter.getName() + "%'";
            }

            query += preparePaginationQuery(filter.getPage(), filter.getPageSize());

        }

        log.trace(query);

        return prepareAccountsData(jdbcTemplate.queryForList(query));
    }

    void update(AccountData data) {
        String query = "UPDATE accounts SET name = ?, preferred_hour_from = ?, preferred_hour_to = ?, all_day = ?, newsletter_accepted = ? " +
                " WHERE id = ?";
        jdbcTemplate.update(query, data.getName(), data.getPreferredHourFrom(), data.getPreferredHourTo(), data.getAllDay(), data.getNewsletterAccepted() ? 1 : 0, data.getId());

    }

    void delete(Long id) {
        String query = "DELETE FROM accounts WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    Long getTotalAccountsCount() {
        String query = "SELECT COUNT(id) AS count FROM accounts;";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> getLong(rs, "count"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private List<AccountData> prepareAccountsData(List<Map<String, Object>> rows) {
        List<AccountData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new AccountData(
                    getLong(row, "id"),
                    getString(row, "name")
            ));
        }
        return list;
    }
}
