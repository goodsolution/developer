package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.UserData;

import java.time.LocalDateTime;

@Service
class UsersJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(UsersJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public UsersJdbcRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

//    UserData getUserById(Long id) {
//        try {
//            String query = "SELECT * FROM users WHERE id = ?";
//            //log.trace(query);
//            return jdbcTemplate.queryForObject(query, new Object[]{id},
//                    (rs, rowNum) -> {
//                        UserData data = new UserData(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("surname"),
//                                rs.getLong("account_id")
//                        );
//                        return data;
//                    });
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }

//    UserData getUserByLogin(String login) {
//        String query = "SELECT * FROM users WHERE username = ?";
//        //log.trace(query);
//        try {
//            return jdbcTemplate.queryForObject(query, new Object[]{login},
//                    (rs, rowNum) -> {
//                        UserData data = new UserData(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("surname"),
//                                rs.getLong("account_id")
//                        );
//                        return data;
//                    });
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }

//    UserData get(Long id) {
//        String query = "SELECT * FROM users WHERE id = ?";
//        //log.trace(query);
//        try {
//            return jdbcTemplate.queryForObject(query, new Object[]{id},
//                    (rs, rowNum) -> {
//                        UserData data = new UserData(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("surname"),
//                                rs.getLong("account_id")
//                        );
//                        return data;
//                    });
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }

//    Long create(UserData user) {
//        String query = "INSERT INTO users (username, password, account_id, role_id, insert_date, update_date, modified_type, modified_by, status, failed_logins, name, surname, last_password_change, e_mail, driver_id, mobile) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(query, user.getLogin(), user.getPasswordHash(), user.getAccount() != null ? user.getAccount().getId() : null, user.getRoleId(), LocalDateTime.now(), LocalDateTime.now(), "register", 1, 1, 0, "First Name", "Last Name", LocalDateTime.now(), user.getLogin(), 0, 0);
//        return commonJdbcRepository.getLastInsertedId();
//    }

//    UserData getUserByLoginAndPassword(String login, String passwordHash) {
//        String query = "SELECT * FROM users WHERE username = ? and password = ?";
//        //log.trace(query);
//
//        try {
//            return jdbcTemplate.queryForObject(query, new Object[]{login, passwordHash},
//                    (rs, rowNum) -> {
//                        UserData data = new UserData(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("surname"),
//                                rs.getLong("account_id")
//                        );
//                        return data;
//                    });
//        } catch (EmptyResultDataAccessException ex) {
//            return null;
//        }
//    }


}
