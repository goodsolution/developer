package pl.com.mike.developer.domain.courseplatform;

public class CustomerLessonDetailsData {
    private Long id;
    private CourseOrderData order;
    private CourseData course;
    private CustomerData customer;
    private LessonData lesson;
    private Boolean watched;

    public CustomerLessonDetailsData(Long id, CourseOrderData order, CourseData course, CustomerData customer, LessonData lesson, Boolean watched) {
        this.id = id;
        this.order = order;
        this.course = course;
        this.customer = customer;
        this.lesson = lesson;
        this.watched = watched;
    }

    public CustomerLessonDetailsData(CourseOrderData order, CourseData course, CustomerData customer, LessonData lesson, Boolean watched) {
        this.order = order;
        this.course = course;
        this.customer = customer;
        this.lesson = lesson;
        this.watched = watched;
    }

    public CustomerLessonDetailsData(CustomerLessonDetailsData data, Boolean watched) {
        this.id = data.getId();
        this.order = data.getOrder();
        this.course = data.getCourse();
        this.customer = data.getCustomer();
        this.lesson = data.getLesson();
        this.watched = watched;
    }

    public Long getId() {
        return id;
    }

    public CourseOrderData getOrder() {
        return order;
    }

    public CourseData getCourse() {
        return course;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public LessonData getLesson() {
        return lesson;
    }

    public Boolean getWatched() {
        return watched;
    }
}
