package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class PurchasedCoursesJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseOrdersJdbcRepository courseOrdersJdbcRepository;
    private CoursesService coursesService;

    public PurchasedCoursesJdbcRepository(JdbcTemplate jdbcTemplate, CourseOrdersJdbcRepository courseOrdersJdbcRepository, CoursesService coursesService) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseOrdersJdbcRepository = courseOrdersJdbcRepository;
        this.coursesService = coursesService;
    }

    void create(PurchasedCourseData data) {
        String query = "INSERT INTO crs_purchased_courses (order_id, course_id, price, returned) values (?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getCourse().getId(), data.getPrice(), data.getReturned());
    }

    List<PurchasedCourseData> find(PurchasedCoursesFilter filter) {
        String query = "SELECT * FROM crs_purchased_courses";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getId() != null) {
                query += " AND id = " + filter.getId();
            }

            if(filter.getOrderId() != null) {
                query += " AND order_id = " + filter.getOrderId();
            }
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<PurchasedCourseData> list = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            list.add(new PurchasedCourseData(
                    getLong(row, "id"),
                    courseOrdersJdbcRepository.find(new CourseOrdersFilter(getLong(row, "order_id"), null)).get(0),
                    coursesService.find(new CoursesFilter(getLong(row, "course_id"))).get(0),
                    getBigDecimal(row, "price"),
                    getBoolean(row, "returned")
            ));
        }
        return list;
    }

    void update(PurchasedCourseData data) {
        String query = "UPDATE crs_purchased_courses SET order_id = ?, course_id = ?, price = ?, returned = ? WHERE id = ?";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getCourse().getId(), data.getPrice(), data.getReturned(), data.getId());
    }
}
