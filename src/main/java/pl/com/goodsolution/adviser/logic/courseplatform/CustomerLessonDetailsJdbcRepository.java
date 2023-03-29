package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class CustomerLessonDetailsJdbcRepository {

    private JdbcTemplate jdbcTemplate;
    private CourseOrdersJdbcRepository courseOrdersJdbcRepository;
    private CoursesJdbcRepository coursesJdbcRepository;
    private CourseCustomersJdbcRepository courseCustomersJdbcRepository;
    private LessonsJdbcRepository lessonsJdbcRepository;

    public CustomerLessonDetailsJdbcRepository(JdbcTemplate jdbcTemplate, CourseOrdersJdbcRepository courseOrdersJdbcRepository, CoursesJdbcRepository coursesJdbcRepository, CourseCustomersJdbcRepository courseCustomersJdbcRepository, LessonsJdbcRepository lessonsJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseOrdersJdbcRepository = courseOrdersJdbcRepository;
        this.coursesJdbcRepository = coursesJdbcRepository;
        this.courseCustomersJdbcRepository = courseCustomersJdbcRepository;
        this.lessonsJdbcRepository = lessonsJdbcRepository;
    }

    void create(CustomerLessonDetailsData data) {
        String query = "INSERT INTO crs_customer_lesson_details (order_id, course_id, customer_id, lesson_id, watched) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getCourse().getId(), data.getCustomer().getId(), data.getLesson().getId(), data.getWatched());
    }

    List<CustomerLessonDetailsData> find(CustomerLessonDetailsFilter filter) {

        String query = "SELECT * FROM crs_customer_lesson_details";

        if(filter != null) {

            query += " WHERE 1=1";

            if(filter.getCustomerId() != null) {
                query += " AND customer_id = " + filter.getCustomerId();
            }

            if(filter.getLessonId() != null) {
                query += " AND lesson_id = " + filter.getLessonId();
            }

        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<CustomerLessonDetailsData> details = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            details.add(new CustomerLessonDetailsData(
                    getLong(row, "id"),
                    courseOrdersJdbcRepository.find(new CourseOrdersFilter(getLong(row, "order_id"))).get(0),
                    coursesJdbcRepository.find(new CoursesFilter(getLong(row, "course_id"))).get(0),
                    courseCustomersJdbcRepository.find(new CustomersFilter(getLong(row, "customer_id"))).get(0),
                    lessonsJdbcRepository.find(new LessonsFilter(getLong(row, "lesson_id"))).get(0),
                    getBoolean(row, "watched")
            ));
        }
        return details;
    }

    void update(CustomerLessonDetailsData data) {
        String query = "UPDATE crs_customer_lesson_details SET order_id = ?, course_id = ?, customer_id = ?, lesson_id = ?, watched = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getOrder().getId(), data.getCourse().getId(), data.getCustomer().getId(), data.getLesson().getId(), data.getWatched(), data.getId());
    }
}
