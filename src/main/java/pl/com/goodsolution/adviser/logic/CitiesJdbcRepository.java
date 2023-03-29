package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.CityData;

@Service
class CitiesJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(CitiesJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public CitiesJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    CityData getCity(Long id) {
        String query = "SELECT * FROM city WHERE city_id = ? ";
        //log.trace(query);
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> {
                        CityData data = new CityData(
                                rs.getLong("city_id"),
                                rs.getLong("default_driver"),
                                rs.getTimestamp("date_to") != null ? rs.getTimestamp("date_to").toLocalDateTime().toLocalDate() : null,
                                rs.getString("is_discount").equals("1") ? true : false,
                                rs.getBigDecimal("discount")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    void addFailedCityCheck(String city) {
        // 	$q = mysqli_query($db, "INSERT INTO `city_check_failed` (`city`, `failed_by`) VALUES ('" . $city . "', '" . $_SESSION['admin_login']['id'] . "')");
        //TODO finish
    }


}
