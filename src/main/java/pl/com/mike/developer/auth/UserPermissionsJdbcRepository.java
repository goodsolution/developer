package pl.com.mike.developer.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
class UserPermissionsJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(UserPermissionsJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    UserPermissionsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Set<Permissions> getUserPermissions(String userName) {
        String query = "SELECT page_access FROM users u, users_roles r, users_roles_permissions p where u.role_id=r.role_id and r.role_id = p.role_id and username = '" + userName + "'";
        //log.trace(query);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        Set<Permissions> permissions = new HashSet<>();
        for (Map row : rows) {
            Permissions perm = Permissions.valueOf(((String) row.get("page_access")).toUpperCase());
            permissions.add(perm);
        }
        return permissions;
    }

    boolean hasUserRole(String login, String userRole) {
        String query = "SELECT Count(*) FROM users u JOIN users_roles r ON r.role_id = u.role_id where username = ? and r.name = ?";
        //log.trace(query);
        Long count = jdbcTemplate.queryForObject(query, Long.class, login, userRole);
        if (count.longValue() == 0) {
            return false;
        }
        return true;
    }
}
