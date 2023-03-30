package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CourseData;
import pl.com.mike.developer.domain.courseplatform.CourseOrderData;
import pl.com.mike.developer.domain.courseplatform.PurchasedCourseData;
import pl.com.mike.developer.domain.courseplatform.PurchasedCoursesFilter;

import java.util.List;

@Service
public class PurchasedCoursesService {

    private PurchasedCoursesJdbcRepository purchasedCoursesJdbcRepository;

    public PurchasedCoursesService(PurchasedCoursesJdbcRepository purchasedCoursesJdbcRepository) {
        this.purchasedCoursesJdbcRepository = purchasedCoursesJdbcRepository;
    }

    public void create(List<CourseData> courses, CourseOrderData order) {
        for (CourseData course : courses) {
            create(new PurchasedCourseData(order, course, course.getPrice(), false));
        }
    }

    public void create(PurchasedCourseData data) {
        purchasedCoursesJdbcRepository.create(data);
    }

    public List<PurchasedCourseData> find(PurchasedCoursesFilter filter) {
        return purchasedCoursesJdbcRepository.find(filter);
    }

    public void update(PurchasedCourseData data) {
        purchasedCoursesJdbcRepository.update(data);
    }

    public void returnPurchasedCourse(Long id) {

        PurchasedCourseData data = find(new PurchasedCoursesFilter(id, null)).get(0);

        if(!data.getOrder().getStatus().equals(OrderStatus.PAID.getValue())) {
            throw new IllegalArgumentException("Can't return, this order has not been paid");
        } else {
            update(new PurchasedCourseData(data, true));
        }
    }
}
