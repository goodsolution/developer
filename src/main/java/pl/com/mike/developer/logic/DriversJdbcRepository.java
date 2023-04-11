package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class DriversJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(DriversJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    public DriversJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    DriverData getDriver(Long id) {
//        String query = "SELECT * FROM delivery_driver WHERE id = ? ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{id},
//                (rs, rowNum) -> {
//                    DriverData data = new DriverData(
//                            rs.getLong("id")
//                    );
//                    return data;
//                });
//    }
//
//    DriverData getActiveDriver(Long id) {
//        String query = "SELECT * FROM delivery_driver WHERE id = ? AND active = '1' LIMIT 1 ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{id},
//                (rs, rowNum) -> {
//                    DriverData data = new DriverData(
//                            rs.getLong("id")
//                    );
//                    return data;
//                });
//    }
//
//    Long getActiveDriverForCity(Long cityId) {
//        String query = "SELECT driver_id FROM delivery_driver_city LEFT JOIN delivery_driver ON delivery_driver.id = delivery_driver_city.driver_id WHERE delivery_driver_city.city_id = ? AND delivery_driver.active = '1' LIMIT 1 ";
//        log.trace(query);
//        try {
//            return jdbcTemplate.queryForObject(query, new Object[]{cityId}, Long.class);
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }
//
//    Long getPayConfigForDriverCount(Long driverId) {
//        String query = "SELECT count(*) FROM drivers_pay_config WHERE driver_id = ? ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{driverId}, Long.class);
//    }
//    void createPayConfigForDriver(Long driverId) {
//        String query = "" +
//                "INSERT INTO drivers_pay_config (normal, same_address, driver_id) " +
//                "VALUES( 0, 0, ?)";
//
//        log.trace(query);
//        jdbcTemplate.update(
//                query,
//                driverId
//        );
//    }
//    DriverPayData getPayConfigForDriver(Long driverId) {
//        String query = "SELECT * FROM drivers_pay_config WHERE driver_id = ? ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{driverId},
//                (rs, rowNum) -> {
//                    DriverPayData data = new DriverPayData(
//                            rs.getLong("id"),
//                            rs.getBigDecimal("normal"),
//                            rs.getBigDecimal("same_address"),
//                            rs.getLong("driver_id")
//                    );
//                    return data;
//                });
//    }
//
//    Long getDriverIdForOrder(Long orderId) {
//        String query = "SELECT * FROM orders WHERE order_id =  ?";
//        log.trace(query);
//        try {
//            return jdbcTemplate.queryForObject(query, new Object[]{orderId},
//                    (rs, rowNum) -> {
//                        return rs.getLong("order_driver_id");
//                    });
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }
//
//    Long getDriverSettlementCountFor(Long driverId, LocalDate date, String address) {
//        String query = "SELECT count(*) FROM drivers_settlement WHERE drivers_settlement.driver_id = ? AND CONVERT( date, date ) = ? AND address = ? ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{driverId, date, address}, Long.class);
//    }
//
//    Long getDriverSettlementForDeliveryCount(Long deliveryId) {
//        String query = "SELECT count(*) FROM drivers_settlement WHERE delivery_id = ? ";
//        log.trace(query);
//        return jdbcTemplate.queryForObject(query, new Object[]{deliveryId}, Long.class);
//    }
//
////    void createDriverSettlement(DriverSettlementCreateData data) {
////        String query = "INSERT INTO `drivers_settlement` (`delivery_id`, `date`, `amount`, `driver_id`, `address`) " +
////                "VALUES (" +
////                "?, " +
////                "?, " +
////                "?, " +
////                "?, " +
////                "? " +
////                ")";
////
////        log.trace(query);
////        jdbcTemplate.update(
////                query,
////                data.getDeliveryId(),
////                data.getDateTime(),
////                data.getAmount(),
////                data.getDriverId(),
////                data.getAddress()
////        );
////    }

}
