package pl.com.mike.developer.api.courseplatform;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.mike.developer.api.PurchasedCoursesAdminRequestGetResponse;
import pl.com.mike.developer.domain.InvoiceData;
import pl.com.mike.developer.domain.JobOfferData;
import pl.com.mike.developer.domain.courseplatform.*;
import pl.com.mike.developer.logic.courseplatform.*;
import pl.com.mike.developer.logic.courseplatform.notifications.NotificationsFromPanelService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api/crs")
public class CoursePlatformEndpointAdmin {

    private CoursesService coursesService;
    private LessonsService lessonsService;
    private AuthorsService authorsService;
    private CourseOrdersService courseOrdersService;
    private InvoicesService invoicesService;
    private PurchasedCoursesService purchasedCoursesService;
    private MemesService memesService;
    private CourseCommentsService courseCommentsService;
    private LessonCommentsService lessonCommentsService;
    private CourseAttachmentsService courseAttachmentsService;
    private StatisticService statisticService;
    private ModulesService modulesService;
    private MarketingService marketingService;
    private LessonAttachmentsService lessonAttachmentsService;
    private NotificationsFromPanelService notificationsFromPanelService;
    private CourseCustomersService courseCustomersService;
    private JobOffersService jobOffersService;
    private CustomerGroupsService customerGroupsService;
    private CustomerToGroupService customerToGroupService;
    private TraceService traceService;

    public CoursePlatformEndpointAdmin(CoursesService coursesService, LessonsService lessonsService, AuthorsService authorsService, CourseOrdersService courseOrdersService, InvoicesService invoicesService, PurchasedCoursesService purchasedCoursesService, MemesService memesService, CourseCommentsService courseCommentsService, LessonCommentsService lessonCommentsService, CourseAttachmentsService courseAttachmentsService, StatisticService statisticService, ModulesService modulesService, MarketingService marketingService, LessonAttachmentsService lessonAttachmentsService, NotificationsFromPanelService notificationsFromPanelService, CourseCustomersService courseCustomersService, JobOffersService jobOffersService, CustomerGroupsService customerGroupsService, CustomerToGroupService customerToGroupService, TraceService traceService) {
        this.coursesService = coursesService;
        this.lessonsService = lessonsService;
        this.authorsService = authorsService;
        this.courseOrdersService = courseOrdersService;
        this.invoicesService = invoicesService;
        this.purchasedCoursesService = purchasedCoursesService;
        this.memesService = memesService;
        this.courseCommentsService = courseCommentsService;
        this.lessonCommentsService = lessonCommentsService;
        this.courseAttachmentsService = courseAttachmentsService;
        this.statisticService = statisticService;
        this.modulesService = modulesService;
        this.marketingService = marketingService;
        this.lessonAttachmentsService = lessonAttachmentsService;
        this.notificationsFromPanelService = notificationsFromPanelService;
        this.courseCustomersService = courseCustomersService;
        this.jobOffersService = jobOffersService;
        this.customerGroupsService = customerGroupsService;
        this.customerToGroupService = customerToGroupService;
        this.traceService = traceService;
    }

    @PostMapping("/course")
    public void createCourse(@RequestBody CoursePostRequest request) {
        coursesService.create(new CourseData(request.getTitle(), request.getDescription(), request.getPrice(), request.getLanguage(), request.getAuthorId(), request.getCode(), request.getType()));
    }

    @PostMapping("/course/{id}/duplicate")
    public void duplicateCourse(@PathVariable Long id) {
        Long duplicatedCourseId = coursesService.duplicate(id);
        lessonsService.duplicateLessons(id, duplicatedCourseId);
        courseAttachmentsService.duplicateCourseAttachments(id, duplicatedCourseId);
    }

    @GetMapping("/courses")
    public List<CourseGetResponse> getCourses(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "visibility_status", required = false) String visibilityStatus,
            @RequestParam(name = "author_id", required = false) Long authorId
    ) {
        List<CourseData> courses = coursesService.findForPanelAdmin(new CoursesFilter(false, page, pageSize, type, CourseVisibilityStatus.from(visibilityStatus), authorId));
        return coursesToResponses(courses);
    }

    @GetMapping("/customers")
    public List<CustomerGetResponse> getCustomers(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize,
            @RequestParam(name = "login", required = false) String loginLike,
            @RequestParam(name = "invoice_first_and_last_name", required = false) String invoiceFirstAndLastName
    ) {
        List<CustomerData> customers = courseCustomersService.find(new CustomersFilter(loginLike, page, pageSize, invoiceFirstAndLastName));
        return customersToResponses(customers);
    }

    @PostMapping("/customer")
    public void createCustomer(@RequestBody CustomerPostRequest request, HttpServletRequest httpServletRequest) {
        courseCustomersService.registerCustomer(new CustomerData(request.getLogin(),
                request.getPasswordHash(),
                request.getLanguage(),
                request.getRegulationAccepted(),
                request.getNewsletterAccepted(),
                request.getInvoiceFirstAndLastName(),
                request.getIsEnabled(),
                request.getIsEmailConfirmed(),
                request.getAuthorities(),
                httpServletRequest,
                prepareCustomerToGroupData(request))
        );

    }



    @PutMapping("/customer/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerPutRequest request) {
        CustomerData data = courseCustomersService.get(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        courseCustomersService.update(new CustomerData(id, request.getLogin(), data.getPasswordHash(), request.getLanguage(), request.getInvoiceType(), request.getInvoiceCompanyName(), request.getInvoiceStreet(), request.getInvoicePostalCode(), request.getInvoiceCity(), request.getInvoiceNip(), request.getInvoiceCountry(), request.getRegulationAccepted(), request.getNewsletterAccepted(), request.getInvoiceFirstAndLastName(), request.getIsEnabled(), request.getIsEmailConfirmed(), LocalDateTime.parse(request.getRegistrationDatetime(), formatter), request.getAuthorities(), prepareCustomerToGroupData(id, request)));
    }



    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        courseCustomersService.delete(id);
    }

    @PutMapping("/customer-change-password/{id}")
    public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        courseCustomersService.changePasswordAdmin(id, request.getNewPasswordHash());
    }

    @PutMapping("/course/{id}")
    public void updateCourse(@PathVariable Long id, @RequestBody CoursePutRequest request) {
        coursesService.updateFromPanelAdmin(new CourseData(id, request.getTitle(), request.getDescription(), request.getPrice(), request.getLanguage(), request.getVisibilityStatus(), request.getAuthorId(), request.getCode(), request.getSaleStatus()));
    }

    @DeleteMapping("/course/{id}")
    public void deleteCourse(@PathVariable Long id) {
        coursesService.delete(id);
    }

    @GetMapping("/lessons/{courseId}")
    public LessonsRequestGetResponse getLessons(@PathVariable(name = "courseId") Long courseId) {

        List<LessonData> lessonsWithoutModule = lessonsService.find(new LessonsFilter(courseId, true, false));
        List<ModuleData> modulesInCourse = modulesService.find(new ModulesFilter(courseId, false));

        List<ModuleGetResponse> modulesInCourseResponses = new ArrayList<>();

        for (ModuleData moduleInCourse : modulesInCourse) {
            List<LessonData> lessons = lessonsService.find(new LessonsFilter(false, moduleInCourse.getId()));
            modulesInCourseResponses.add(new ModuleGetResponse(moduleInCourse, lessonsToResponses(lessons)));
        }

        return new LessonsRequestGetResponse(lessonsToResponses(lessonsWithoutModule), modulesInCourseResponses);
    }

    @DeleteMapping("/lesson/{id}")
    public void deleteLesson(@PathVariable(name = "id") Long id) {
        lessonsService.delete(id);
    }

    @PostMapping("/lesson")
    public void createLesson(@RequestBody LessonPostRequest request) {
        lessonsService.create(new LessonData(request.getTitle(), request.getDescription(), request.getMovieLink(),
                request.getModuleId(), request.getCourseId(), MovieLinkType.from(request.getMovieLinkType())));
    }

    @PutMapping("/lesson/{id}")
    public void updateLesson(@PathVariable Long id, @RequestBody LessonPutRequest request) {
        lessonsService.updateFromPanelAdmin(new LessonData(id, request.getTitle(), request.getDescription(),
                request.getMovieLink(), request.getVisibilityStatus(), request.getModuleId(), request.getType(),
                request.getTaskSolutionDescription(), request.getTaskSolutionMovieLink(),
                MovieLinkType.from(request.getMovieLinkType()), MovieLinkType.from(request.getTaskSolutionMovieLinkType())));
    }

    @PutMapping("/lesson/change-order/{id}")
    public void changeOrder(@PathVariable Long id, @RequestBody LessonChangeOrderRequest request) {
        lessonsService.changeOrder(id, request.getDirection());
    }

    @PutMapping("/lesson/{id}/change-module")
    public void changeLessonModule(@PathVariable Long id, @RequestParam Long moduleId) {
        lessonsService.changeModule(id, moduleId);
    }

    @PostMapping("/lesson/attachment")
    public void postLessonAttachment(@RequestParam("file") MultipartFile file,
                                     @RequestParam String name,
                                     @RequestParam Long lessonId) {
        lessonAttachmentsService.create(new LessonAttachmentData(file, lessonId, name));
    }

    @DeleteMapping("/lesson/attachment/{id}")
    public void deleteLessonAttachment(@PathVariable Long id) {
        lessonAttachmentsService.delete(id);
    }

    @GetMapping("/lesson/{lessonId}/attachments")
    public LessonAttachmentsGetResponse getLessonAttachments(@PathVariable Long lessonId) {
        List<LessonAttachmentData> lessonAttachments = lessonAttachmentsService.find(new LessonAttachmentsFilter(lessonId, false));
        return new LessonAttachmentsGetResponse(ConverterToResponsesUtil.lessonAttachmentsToResponsesAdmin(lessonAttachments));
    }

    @GetMapping("/orders")
    public OrdersGetResponse getOrders(@RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "number", required = false) String number,
                                       @RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "invoice_issued", required = false) Boolean invoiceIssued,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                                       @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {
        List<CourseOrderData> orders = courseOrdersService.find(new CourseOrdersFilter(id, number, status, invoiceIssued, page, pageSize));
        return new OrdersGetResponse(ordersToResponses(orders));
    }

    @DeleteMapping("/order/{id}/cancel")
    public void cancelOrder(@PathVariable Long id) {
        courseOrdersService.cancelOrderInPayu(id);
    }

    @GetMapping("/invoices")
    public InvoicesAdminGetResponse getInvoices(@RequestParam(value = "order_id", required = false) Long orderId) {
        List<InvoiceData> invoices = invoicesService.find(new InvoicesFilter(null, orderId, false));
        return new InvoicesAdminGetResponse(invoicesToResponses(invoices));
    }

    @DeleteMapping("/invoice/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoicesService.delete(id);
    }

    @GetMapping("/purchased-courses")
    public PurchasedCoursesAdminRequestGetResponse getPurchasedCourses(@RequestParam(value = "order_id", required = false) Long orderId) {
        List<PurchasedCourseData> purchasedCourses = purchasedCoursesService.find(new PurchasedCoursesFilter(orderId));
        return new PurchasedCoursesAdminRequestGetResponse(purchasedCoursesToResponses(purchasedCourses));
    }

    @PutMapping("/return/purchased-course/{id}")
    public void returnPurchasedCourse(@PathVariable Long id) {
        purchasedCoursesService.returnPurchasedCourse(id);
    }

    @GetMapping("/authors")
    public AuthorsRequestGetResponse getAuthors(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize
    ) {
        List<AuthorData> authors = authorsService.find(new AuthorsFilter(page, pageSize));
        return new AuthorsRequestGetResponse(authorsToResponses(authors));
    }

    @GetMapping("/customer-groups")
    public CustomerGroupsRequestGetResponse getCustomerGroups(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize
    ) {
        List<CustomerGroupData> customerGroups = customerGroupsService.find(new CustomerGroupsFilter(page, pageSize));
        return new CustomerGroupsRequestGetResponse(customerGroupsToResponses(customerGroups));
    }
    @PostMapping("/customer-groups")
    public void createCustomerGroups(@RequestBody CustomerGroupPostRequest request) {
        customerGroupsService.create(new CustomerGroupData(request.getName()));
    }
    @DeleteMapping("/customer-groups/{id}")
    public void deleteCustomerGroups(@PathVariable Long id) {
        customerGroupsService.delete(id);
    }
    @PutMapping("/customer-groups/{id}")
    public void updateCustomerGroups(@PathVariable Long id, @RequestBody CustomerGroupPutRequest request) {
        customerGroupsService.update(new CustomerGroupData(id, request.getName()));
    }
    @PutMapping("/customer-group/{id}")
    public void updateCustomerGroup(@PathVariable Long id, @RequestBody CustomerGroupPutRequest request) {
        customerGroupsService.update(new CustomerGroupData(id, request.getName()));
    }
    @DeleteMapping("/customer-group/{id}")
    public void deleteCustomerGroup(@PathVariable Long id) {
        customerGroupsService.delete(id);
    }

    @PostMapping("/author")
    public void createAuthor(@RequestBody AuthorPostRequest request) {
        authorsService.create(new AuthorData(request.getFirstName(), request.getLastName()));
    }

    @PutMapping("/author/{id}")
    public void updateAuthor(@PathVariable Long id, @RequestBody AuthorPutRequest request) {
        authorsService.update(new AuthorData(id, request.getFirstName(), request.getLastName()));
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorsService.delete(id);
    }

    @PostMapping("/meme")
    public void createMeme(@RequestParam("file") MultipartFile file,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam String keywords,
                           @RequestParam String language,
                           @RequestParam String code) {
        memesService.create(new MemeData(title, description, keywords, language, file, code));
    }

    @GetMapping("/memes")
    public MemesRequestGetResponse getMemes(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {
        List<MemeData> memes = memesService.find(new MemesFilter(page, pageSize));
        return new MemesRequestGetResponse(memesToResponses(memes));
    }

    @PostMapping("/job-offer")
    public void createJobOffer(@RequestBody JobOfferPostRequest request) {
        jobOffersService.create(new JobOfferData(request.getTitle(), request.getContent()));
    }

    @GetMapping("/job-offers")
    public JobOffersGetResponse getJobOffers(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize) {
        List<JobOfferData> jobOffers = jobOffersService.find(new JobOffersFilter(page, pageSize, false));
        return new JobOffersGetResponse(ConverterToResponsesUtil.jobOffersToResponses(jobOffers));
    }

    @DeleteMapping("/meme/{id}")
    public void deleteMeme(@PathVariable Long id) {
        memesService.delete(id);
    }

    @DeleteMapping("/job-offer/{id}")
    public void deleteJobOffer(@PathVariable Long id) {
        jobOffersService.delete(id);
    }

    @PutMapping("/meme/{id}/photo")
    public void updateMemePhoto(@RequestParam("file") MultipartFile memePhoto, @PathVariable Long id) {
        memesService.updatePhoto(id, memePhoto);
    }

    @PutMapping("/meme/{id}")
    public void updateMeme(@PathVariable Long id, @RequestBody MemePutRequest request) {
        memesService.update(id, new MemeDataToChange(request.getTitle(), request.getLanguage(), request.getDescription(), request.getKeywords(), request.getCode()));
    }

    @GetMapping("/course-comments")
    public CourseCommentsRequestGetResponseAdmin getCourseComments(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize
    ) {
        List<CourseCommentData> courseComments = courseCommentsService.findForPanelAdmin(page, pageSize);
        return new CourseCommentsRequestGetResponseAdmin(ConverterToResponsesUtil.courseCommentsToResponsesAdmin(courseComments));
    }

    @PutMapping("/course-comment/{id}")
    public void updateCourseComment(@PathVariable Long id, @RequestBody CourseCommentPutRequest request) {
        courseCommentsService.update(id, request.getVisibilityStatus());
    }

    @DeleteMapping("/course-comment/{id}")
    public void deleteCourseComment(@PathVariable Long id) {
        courseCommentsService.delete(id);
    }

    @GetMapping("/lesson-comments")
    public LessonCommentsRequestGetResponseAdmin getLessonComments(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Long pageSize
    ) {
        List<LessonCommentData> lessonComments = lessonCommentsService.findForPanelAdmin(page, pageSize);
        return new LessonCommentsRequestGetResponseAdmin(ConverterToResponsesUtil.lessonCommentsToResponsesAdmin(lessonComments));
    }

    @PutMapping("/lesson-comment/{id}")
    public void updateLessonComment(@PathVariable Long id, @RequestBody LessonCommentPutRequest request) {
        lessonCommentsService.update(id, request.getStatus());
    }

    @DeleteMapping("/lesson-comment/{id}")
    public void deleteLessonComment(@PathVariable Long id) {
        lessonCommentsService.delete(id);
    }

    @PostMapping("/course/attachment")
    public void postCourseAttachment(@RequestParam("file") MultipartFile file,
                                     @RequestParam String name,
                                     @RequestParam Long courseId) {
        courseAttachmentsService.create(new CourseAttachmentData(name, courseId, file));
    }

    @GetMapping("/course/{id}/attachments")
    public CourseAttachmentsRequestGetResponse getCourseAttachments(@PathVariable(value = "id") Long courseId) {
        List<CourseAttachmentData> courseAttachments = courseAttachmentsService.findForCourse(courseId);
        return new CourseAttachmentsRequestGetResponse(ConverterToResponsesUtil.courseAttachmentsToResponses(courseAttachments));
    }

    @DeleteMapping("/course/attachment/{id}")
    public void deleteCourseAttachment(@PathVariable(value = "id") Long courseAttachmentId) {
        courseAttachmentsService.delete(courseAttachmentId);
    }

    @GetMapping("/statistic/{type}")
    public StatisticData refreshStatistic(@PathVariable String type,
                                          @RequestParam(required = false) String param,
                                          @RequestParam(required = false, name = "value") Integer value) {
        return statisticService.refresh(StatisticType.valueOf(type), param, value);
    }

    @GetMapping("/statistic/top-selling-products-for-money")
    public List<StatisticSellingData> topSellingProductsForMoney() {
        return statisticService.findTopSellingProductsForMoney();
    }

    @GetMapping("/statistic/new-customers")
    public List<StatisticNewCustomerGetResponse> newCustomers() {
        return ConverterToResponsesUtil.statisticNewCustomersToResponses(statisticService.findNewCustomers());
    }

    @GetMapping("/statistic/visited-landings-count")
    public List<StatisticItemCountData> visitedLandings() {
        return statisticService.findVisitedLandings();
    }

    @GetMapping("/statistic/courses/completion")
    public StatisticCourseCompletionRequestGetResponse courseCompletion(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                                                                        @RequestParam(name = "page_size", required =
                                                                                false, defaultValue = "10") Long pageSize) {
        List<StatisticCourseCompletionData> courses =
                statisticService.findCourseCompletion(new StatisticCompletionFilter(page, pageSize));
        return new StatisticCourseCompletionRequestGetResponse(ConverterToResponsesUtil.courseCompletionToResponses(courses));
    }

    @PostMapping("/marketing/newsletter")
    public void newsletter(@RequestBody NewsletterData request) {
        marketingService.sendNewsletter(request);
    }

    @PostMapping("/module")
    public void createModule(@RequestBody ModulePostRequest request) {
        modulesService.create(new ModuleData(request.getCourseId(), request.getName()));
    }

    @GetMapping("/modules")
    public ModulesRequestGetResponse findModules(@RequestParam Long courseId) {
        List<ModuleData> modules = modulesService.find(new ModulesFilter(courseId, false));
        return new ModulesRequestGetResponse(ConverterToResponsesUtil.modulesToResponses(modules));
    }

    @PutMapping("/module/{id}")
    public void updateModule(@PathVariable Long id, @RequestBody ModulePutRequest request) {
        modulesService.updateFromPanelAdmin(new ModuleData(id, request.getName(), request.getVisibilityStatus()));
    }

    @PutMapping("/module/{id}/change-order")
    public void changeModuleOrder(@PathVariable Long id, @RequestParam String direction) {
        modulesService.changeOrder(id, direction);
    }

    @DeleteMapping("/module/{id}")
    public void deleteModule(@PathVariable Long id) {
        modulesService.delete(id);
        lessonsService.deleteLessonsInModule(id);
    }

    @PostMapping("/notifications")
    public void createNotifications(@RequestBody NotificationsPostRequest request) {
        notificationsFromPanelService.createNotifications(new CreateNotificationsParameters(request));
    }

    @GetMapping("/statistics/trace-per-day")
    public TracePerDayResponse findTracesPerDay(@RequestParam Month month) {
        List<TraceData> traces = traceService.findTracesPerDay(month);
        return new TracePerDayResponse(ConverterToResponsesUtil.traceToResponses(traces));
    }

    @GetMapping("/statistics/trace-per-month")
    public TracePerDayResponse findTracesPerMonth(@RequestParam Long year) {
        List<TraceData> trace = traceService.findTracesPerMonth(year);
        return new TracePerDayResponse(ConverterToResponsesUtil.traceToResponses(trace));
//        TracePerDayResponse list = new TracePerDayResponse(Arrays.asList(new TracePerDayGetResponse(
//                "2022-11-11",
//                "what",
//                "value",
//                "5")));
    }

    private static List<CustomerToGroupData> prepareCustomerToGroupData(Long id, CustomerPutRequest request) {
        List<CustomerToGroupData> customerToGroups = new ArrayList<>();
        for (Long groupId : request.getGroupIds()) {
            customerToGroups.add(new CustomerToGroupData(id, groupId));
        }
        return  customerToGroups;
    }
    private static List<CustomerToGroupData> prepareCustomerToGroupData(CustomerPostRequest request) {
        List<CustomerToGroupData> customerToGroupData = new ArrayList<>();
        for (Long groupId : request.getGroupIds()) {
            customerToGroupData.add(new CustomerToGroupData(groupId));
        }
        return customerToGroupData;
    }

    private List<CourseGetResponse> coursesToResponses(List<CourseData> courses) {
        List<CourseGetResponse> list = new ArrayList<>();
        for (CourseData course : courses) {
            list.add(new CourseGetResponse(course));
        }
        return list;
    }

    private List<CustomerGetResponse> customersToResponses(List<CustomerData> customers) {
        List<CustomerGetResponse> list = new ArrayList<>();
        for (CustomerData customer : customers) {
            list.add(new CustomerGetResponse(customer));
        }
        return list;
    }

    private List<PurchasedCourseGetResponse> purchasedCoursesToResponses(List<PurchasedCourseData> purchasedCourses) {
        List<PurchasedCourseGetResponse> list = new ArrayList<>();

        for (PurchasedCourseData purchasedCourse : purchasedCourses) {
            list.add(new PurchasedCourseGetResponse(purchasedCourse));
        }

        return list;
    }

    private List<CourseOrderGetResponse> ordersToResponses(List<CourseOrderData> orders) {
        List<CourseOrderGetResponse> list = new ArrayList<>();

        for (CourseOrderData order : orders) {
            list.add(new CourseOrderGetResponse(order));
        }

        return list;
    }

    private List<LessonGetResponse> lessonsToResponses(List<LessonData> lessons) {
        List<LessonGetResponse> responses = new ArrayList<>();

        for (LessonData lesson : lessons) {
            responses.add(new LessonGetResponse(lesson));
        }

        return responses;
    }

    private List<InvoiceGetResponse> invoicesToResponses(List<InvoiceData> invoices) {
        List<InvoiceGetResponse> list = new ArrayList<>();

        for (InvoiceData invoice : invoices) {
            list.add(new InvoiceGetResponse(invoice));
        }

        return list;
    }

    private List<AuthorGetResponse> authorsToResponses(List<AuthorData> authors) {
        List<AuthorGetResponse> list = new ArrayList<>();

        for (AuthorData author : authors) {
            list.add(new AuthorGetResponse(author));
        }

        return list;
    }

    private List<CustomerGroupGetResponse> customerGroupsToResponses(List<CustomerGroupData> customerGroups) {
        List<CustomerGroupGetResponse> list = new ArrayList<>();

        for (CustomerGroupData customerGroup : customerGroups) {
            list.add(new CustomerGroupGetResponse(customerGroup));
        }

        return list;
    }

    private List<MemeGetResponse> memesToResponses(List<MemeData> memes) {
        List<MemeGetResponse> list = new ArrayList<>();

        for (MemeData meme : memes) {
            list.add(new MemeGetResponse(meme));
        }

        return list;
    }

}
