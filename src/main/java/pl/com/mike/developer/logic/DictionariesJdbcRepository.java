package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.DictionaryData;
import pl.com.mike.developer.logic.adviser.JdbcUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
class DictionariesJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(DictionariesJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public DictionariesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<DictionaryData> getPaymentMethods(Language lang) {
        String query = "SELECT * FROM payment_methods_languages WHERE language = '" + lang.getCode() + "'";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("payment_method_id"))),
                    (String) row.get("color"),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getPaymentStatuses(Language lang) {
        String query = "SELECT * FROM order_payment_statues";
        //log.trace(query);
        //List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
//        for (Map row : rows) {
//            DictionaryData dictionaryData = new DictionaryData(
//            Long.parseLong(String.valueOf(row.get("id"))),
//                    (String) row.get("color"),
//                    (String) row.get("name"),
//                    lang.getCode()
//            );
//            //log.trace(dictionaryData.toString());
//            dictionaryDataList.add(dictionaryData);
//        }

        // in database are invalid statuses
        //$paid_selected = $order['payment_status'] == "1" ? "selected" : "";
        //$not_paid_selected = $order['payment_status'] == "0" ? "selected" : "";
        // so 0 - not paid
        //    1 - paid
        //    2 - ?

        DictionaryData dictionaryData = new DictionaryData(
                0L,
                "#e74c3c",
                "Nieopłacone",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);

        dictionaryData = new DictionaryData(
                1L,
                "#2fdc78",
                "Opłacone",
                lang.getCode()
        );


        dictionaryDataList.add(dictionaryData);

        return dictionaryDataList;
    }

    List<DictionaryData> getShipmentTypes(Language lang) {
        String query = "SELECT * FROM types_of_shipments_languages WHERE language = '" + lang.getCode() + "'";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("shipment_type_id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getOrderStatuses(Language lang) {
        String query = "SELECT * FROM orders_statuses";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            String name = (String) row.get("name");
            long id = Long.parseLong(String.valueOf(row.get("order_status_id")));
            if (id == 1) {
                name = "Nowe";
            } else if (id == 2) {
                name = "Przyjęte";
            } else if (id == 3) {
                name = "Gotowe";
            } else if (id == 4) {
                name = "Zrealizowane";
            } else if (id == 5) {
                name = "Zaakceptowane";
            } else if (id == 6) {
                name = "Anulowane";
            } else if (id == 7) {
                name = "Wstrzymane";
            }
            DictionaryData dictionaryData = new DictionaryData(
                    id,
                    (String) row.get("color"),
                    name,
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getActiveDrivers(Language lang) {
        String query = "SELECT * FROM delivery_driver WHERE active = 1";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("id"))),
                    (String) row.get("phone"),
                    (String) row.get("name") + " " + (String) row.get("surname"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getCities(Language lang) {
        String query = "SELECT city_id, city FROM city";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("city_id"))),
                    (String) row.get("city"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getActiveDiets(Language lang) {
        String query = "SELECT * FROM categories WHERE active = 1";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("category_id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getActiveExtras(Language lang) {
        String query = "SELECT * FROM extras WHERE active = 1";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("id"))),
                    (String) row.get("name"),
                    lang.getCode(),
                    BigDecimal.valueOf((Float) row.get("gross_price"))
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getActiveProducts(Language lang) {
        String query = "SELECT * FROM products WHERE active = 1 and table_show = 1";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("product_id"))),
                    (String) row.get("name"),
                    lang.getCode(),
                    Long.parseLong(String.valueOf(row.get("category_id")))
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getDishGroups(Language lang) {
        String query = "SELECT * FROM dietetics_sets_group_names ORDER BY priority";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("id"))),
                    (String) row.get("short"),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getCustomerGroupStatuses(Language lang) {
        String query = "SELECT * FROM customers_groups_statuses";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getCustomerGroups(Language lang) {
        String query = "SELECT * FROM customers_groups";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("group_id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getCustomerStatuses(Language lang) {
        String query = "SELECT * FROM customers_statuses";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("customer_status_id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getAllergens(Language lang) {
        String query = "SELECT * FROM dietetics_allergen_list WHERE table_show = 1";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("id"))),
                    (String) row.get("name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getRoles(Language lang) {
        String query = "SELECT * FROM users_roles";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    Long.parseLong(String.valueOf(row.get("role_id"))),
                    (String) row.get("name"),
                    null,
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

    List<DictionaryData> getAuthors(Language lang) {
        String query = "SELECT * FROM crs_authors;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (Map row : rows) {
            DictionaryData dictionaryData = new DictionaryData(
                    JdbcUtil.getLong(row, "id"),
                    JdbcUtil.getString(row, "first_name") + " " + JdbcUtil.getString(row, "last_name"),
                    lang.getCode()
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }
}