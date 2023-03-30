package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.*;

import java.util.List;

@Service
public class CustomerLessonDetailsService {


    private CustomerLessonDetailsJdbcRepository customerLessonDetailsJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private LessonsJdbcRepository lessonsJdbcRepository;
    private CourseOrdersService courseOrdersService;

    public CustomerLessonDetailsService(CustomerLessonDetailsJdbcRepository customerLessonDetailsJdbcRepository, CourseCustomersService courseCustomersService, LessonsJdbcRepository lessonsJdbcRepository, CourseOrdersService courseOrdersService) {
        this.customerLessonDetailsJdbcRepository = customerLessonDetailsJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.lessonsJdbcRepository = lessonsJdbcRepository;
        this.courseOrdersService = courseOrdersService;
    }

    public void create(Long customerId, Long lessonId) {
        CustomerData customer = courseCustomersService.find(new CustomersFilter(customerId)).get(0);
        LessonData lesson = lessonsJdbcRepository.find(new LessonsFilter(lessonId)).get(0);
        CourseData course = lesson.getCourse();
        CourseOrderData order = courseOrdersService.getOrderMadeByCustomerWithCourse(customer, course);
        CustomerLessonDetailsData data = new CustomerLessonDetailsData(order, course, customer, lesson, false);
        customerLessonDetailsJdbcRepository.create(data);
    }

    public List<CustomerLessonDetailsData> find(CustomerLessonDetailsFilter filter) {
        return customerLessonDetailsJdbcRepository.find(filter);
    }

    public void update(CustomerLessonDetailsData data) {
        customerLessonDetailsJdbcRepository.update(data);
    }

    public void changeLessonWatchedStatus(Long lessonId) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if(loggedCustomer == null) {
            throw new IllegalArgumentException("You must be logged in to set lesson as watched");
        }

        CustomerLessonDetailsData details = find(new CustomerLessonDetailsFilter(lessonId, loggedCustomer.getId())).get(0);
        update(new CustomerLessonDetailsData(details, !details.getWatched()));
    }
}
