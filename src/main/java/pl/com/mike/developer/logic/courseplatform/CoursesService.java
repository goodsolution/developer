package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.com.mike.developer.domain.courseplatform.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoursesService {

    private CoursesJdbcRepository coursesJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private AuthorsService authorsService;
    private FilesService filesService;
    private CustomerAuthoritiesService customerAuthoritiesService;

    public CoursesService(CoursesJdbcRepository coursesJdbcRepository, CourseCustomersService courseCustomersService, AuthorsService authorsService, FilesService filesService, CustomerAuthoritiesService customerAuthoritiesService) {
        this.coursesJdbcRepository = coursesJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.authorsService = authorsService;
        this.filesService = filesService;
        this.customerAuthoritiesService = customerAuthoritiesService;
    }

    public void create(CourseData data) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        AuthorData author;

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            author = authorsService.find(new AuthorsFilter(data.getAuthorId())).get(0);
        } else if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            author = authorsService.find(new AuthorsFilter(loggedCustomer.getAuthor().getId())).get(0);
        } else {
            throw new IllegalArgumentException("You don't have permission to this endpoint!");
        }

        CourseData courseToCreate = new CourseData(data.getTitle(), data.getDescription(), "", data.getPrice(), data.getLanguage(), author, CourseVisibilityStatus.INVISIBLE, data.getCode(), data.getType(), CourseSaleStatus.OPEN);
        validate(courseToCreate);
        coursesJdbcRepository.create(courseToCreate);
    }

    public CourseData get(Long id) {
        return coursesJdbcRepository.get(id);
    }

    public List<CourseData> find(CoursesFilter filter) {
        return coursesJdbcRepository.find(filter);
    }

    public List<CourseData> findForPanelAdmin(CoursesFilter filter) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            return coursesJdbcRepository.find(filter);
        } else if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            return coursesJdbcRepository.find(new CoursesFilter(filter, loggedCustomer.getAuthor().getId()));
        } else {
            throw new IllegalArgumentException("You don't have permission to this endpoint");
        }

    }

    public void updateFromPanelAdmin(CourseData dataToUpdate) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        AuthorData author;

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            author = authorsService.find(new AuthorsFilter(dataToUpdate.getAuthorId())).get(0);
        } else if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            author = authorsService.find(new AuthorsFilter(loggedCustomer.getAuthor().getId())).get(0);
        } else {
            throw new IllegalArgumentException("You don't have permission to this endpoint");
        }

        CourseData oldData = coursesJdbcRepository.find(new CoursesFilter(dataToUpdate.getId())).get(0);
        CourseData updatedData = new CourseData(dataToUpdate.getId(), dataToUpdate.getTitle(), dataToUpdate.getDescription(),
                oldData.getImageName(), dataToUpdate.getPrice(), dataToUpdate.getLanguage(), author,
                dataToUpdate.getVisibilityStatus(), oldData.getDeleteDatetime(), dataToUpdate.getCode(), oldData.getType(), dataToUpdate.getSaleStatus());

        validate(updatedData);
        coursesJdbcRepository.update(updatedData);
    }

    public void delete(Long id) {
        CourseData courseData = find(new CoursesFilter(id)).get(0);
        coursesJdbcRepository.update(new CourseData(courseData, LocalDateTime.now()));
    }

    public Long duplicate(Long id) {
        CourseData course = get(id);

        if(!course.getType().equals(CourseType.BUY_OUR_CODE.getCode())) {
            throw new IllegalArgumentException("Duplication is possible only for 'Buy our code'");
        }

        String title = course.getTitle() + "(Duplicated)";
        String code = course.getCode() + "-duplicated";

        CourseData duplicatedData = new CourseData(course, title, CourseVisibilityStatus.INVISIBLE, code);
        validate(duplicatedData);
        return coursesJdbcRepository.create(duplicatedData);

    }

    public void updateAndSaveImage(Long id, MultipartFile image) {
        if(!image.isEmpty()) {
            String imageName = filesService.saveImageOnServer(image);
            CourseData course = find(new CoursesFilter(id)).get(0);
            coursesJdbcRepository.update(new CourseData(course, imageName));
        }
    }

    public List<CourseData> findCoursesOwnedByLoggedCustomer() {
        return findCoursesOwnedByLoggedCustomer(null);
    }

    public List<CourseData> findCoursesOwnedByLoggedCustomer(String courseType) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        return findCoursesOwnedByCustomer(loggedCustomer, courseType);
    }

    public List<CourseData> findCoursesOwnedByCustomer(CustomerData customer) {
        return findCoursesOwnedByCustomer(customer, null);
    }

    public List<CourseData> findCoursesOwnedByCustomer(CustomerData customer, String courseType) {
        return findCoursesOrderedByCustomer(customer, OrderStatus.PAID, courseType);
    }

    public List<CourseData> findCoursesOrderedByCustomer(CustomerData customer, OrderStatus orderStatus) {
        return findCoursesOrderedByCustomer(customer, orderStatus, null);
    }

    public List<CourseData> findCoursesOrderedByCustomer(CustomerData customer, OrderStatus orderStatus, String courseType) {
        return coursesJdbcRepository.findCoursesOrderedByCustomer(customer.getId(), orderStatus, courseType);
    }

    public List<CourseData> findCoursesInOrder(Long orderId) {
        return coursesJdbcRepository.findCoursesInOrder(orderId);
    }

    public Boolean hasCustomerCourse(CustomerData customer, CourseData course) {
        List<CourseData> coursesOwnedByCustomer = findCoursesOwnedByCustomer(customer);

        for (CourseData courseOwnedByCustomer : coursesOwnedByCustomer) {
            if(courseOwnedByCustomer.getId().equals(course.getId())) {
                return true;
            }
        }

        return false;
    }

    public Boolean hasLoggedCustomerPermissionsToCourseManagement(Long courseId) {

        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

        if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.ADMIN)) {
            return true;
        } else if(customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.TEACHER)) {
            return isCustomerCourseAuthor(loggedCustomer, courseId);
        } else {
            return false;
        }

    }

    private Boolean isCustomerCourseAuthor(CustomerData customer, Long courseId) {

        List<CourseData> courses = find(new CoursesFilter(courseId));

        if(courses.isEmpty()) {
            throw new IllegalArgumentException("Incorrect course ID");
        }

        CourseData course = courses.get(0);

        return customer.getAuthor().getId().equals(course.getAuthor().getId());

    }

    private void validate(CourseData data) {
        validateTitle(data.getTitle());
        validatePrice(data.getPrice());
        validateImage(data.getImageName());
        validateDescription(data.getDescription());
        validateCode(data.getCode(), data.getId());
        validateType(data.getType());
        validateSaleStatus(data.getSaleStatus());
    }

    private void validatePrice(BigDecimal price) {
        if(price == null) {
            throw new IllegalArgumentException("Price can't be empty or is incorrect");
        }else if(price.scale() != 0 && price.scale() != 2) {
            throw new IllegalArgumentException("Incorrect price");
        }else if(price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can't be negative!");
        }else if(price.compareTo(new BigDecimal("10000000.00")) > 0) {
            throw new IllegalArgumentException("Price too big!");
        }
    }

    private void validateTitle(String title) {
        if(title == null || title.equals("")) {
            throw new IllegalArgumentException("Title can't be empty");
        } else if (title.length() > 200) {
            throw new IllegalArgumentException("Title too long, max 200 characters!");
        }
    }

    private void validateImage(String imageName) {
        if(imageName == null || imageName.equals("null")) {
            throw new IllegalArgumentException("Wrong image name");
        }
    }

    private void validateDescription(String description) {
        if(description.length() > 5000) {
            throw new IllegalArgumentException("Description too long, max 5000 characters");
        }
    }

    private void validateCode(String code, Long courseId) {
        if(code == null || code.equals("")) {
            throw new IllegalArgumentException("Code can't be empty");
        } else if (code.length() > 300) {
            throw new IllegalArgumentException("Code too long, max 300 characters");
        } else if (!isCodeUnique(code, courseId)) {
            throw new IllegalArgumentException("Code must be unique, this code exist in other course");
        }
    }

    private Boolean isCodeUnique(String code, Long courseId) {

        List<CourseData> courses = find(new CoursesFilter(code));

        if(courseId != null) {
            courses.removeIf(course -> course.getId().equals(courseId));
        }

        return courses.size() == 0;

    }

    private void validateType(String type) {
        if(!type.equals(CourseType.COURSE.getCode()) && !type.equals(CourseType.BUY_OUR_CODE.getCode()) && !type.equals(CourseType.SELECTION.getCode())) {
            throw new IllegalArgumentException("Course type incorrect");
        }
    }

    private void validateSaleStatus(String saleStatus) {
        if(saleStatus == null) {
            throw new IllegalArgumentException("Sale status can't be empty");
        } else if(!saleStatus.equals(CourseSaleStatus.OPEN.getCode()) && !saleStatus.equals(CourseSaleStatus.CLOSED.getCode())) {
            throw new IllegalArgumentException("Sale status incorrect");
        }
    }
}
