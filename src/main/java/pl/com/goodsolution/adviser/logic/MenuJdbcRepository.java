package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
class MenuJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(MenuJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    MenuJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*

SELECT dd.name FROM dietetics_menu as dm
JOIN dietetics_menu_dishs dmd ON (dm.menu_id = dmd.menu_id)
JOIN dietetics_dishs dd ON (dd.dish_id = dmd.dish_id)
WHERE dm.menu_date = '2019-12-18' AND dm.product_id =51 AND dm.category_id = 3 AND dm.table_show = 1
AND dmd.group_id = 1
limit 1

     */

    List<Map<String, Object>> getMenuDishNameForGroup(LocalDate date, Long dietId, Long kindId, Long groupId) {
        String query = "SELECT DISTINCT(dd.name) as dish_name " +
                "FROM dietetics_menu as dm " +
                "JOIN dietetics_menu_dishs dmd ON (dm.menu_id = dmd.menu_id) " +
                "JOIN dietetics_dishs dd ON (dd.dish_id = dmd.dish_id)  " +
                "WHERE dm.menu_date = ? AND dm.product_id = ? AND dm.category_id = ? AND dm.table_show = ? " +
                "AND dmd.group_id = ? " +
                "ORDER BY 1 ";

        //log.trace(query);
        return jdbcTemplate.queryForList(query,
                date,
                kindId,
                dietId,
                1,
                groupId);
    }

}
