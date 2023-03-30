package pl.com.mike.developer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import pl.com.mike.developer.api.courseplatform.*;
import pl.com.mike.developer.auth.AuthenticatedUser;
import pl.com.mike.developer.auth.Permissions;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.*;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.*;
import pl.com.mike.developer.domain.vin.CarsFilter;
import pl.com.mike.developer.logic.Language;
import pl.com.mike.developer.logic.*;
import pl.com.mike.developer.logic.adviser.AccountsService;
import pl.com.mike.developer.logic.adviser.AdviserService;
import pl.com.mike.developer.logic.adviser.ApplicationsService;
import pl.com.mike.developer.logic.adviser.ContextConfigsService;
import pl.com.mike.developer.logic.courseplatform.*;
import pl.com.mike.developer.logic.vin.CepikService;
import pl.com.mike.developer.logic.vin.VehicleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    private OrdersService ordersService;
    private CustomersService customersService;
    private DictionariesService dictionariesService;
    private ProductsService productsService;
    private CacheService cacheService;
    private AuthenticatedUser authenticatedUser;
    private GalleryService galleryService;
    private MenuService menuService;
    private AdviserService adviserService;
    private AccountsService accountsService;
    private ApplicationsService applicationsService;
    private ContextConfigsService contextConfigsService;
    private CoursesService coursesService;
    private FilesService filesService;
    private LessonsService lessonsService;
    private BasketService basketService;
    private CourseCustomersService courseCustomersService;
    private ApplicationConfig applicationConfig;
    private EmailService emailService;
    private TemplateEngine templateEngine;
    private InvoicesService invoicesService;
    private CourseOrdersService courseOrdersService;
    private MemesService memesService;
    private AuthorsService authorsService;
    private CourseAttachmentsService courseAttachmentsService;
    private ModulesService modulesService;
    private LessonAttachmentsService lessonAttachmentsService;
    private Environment environment;
    private EmailConfirmationService emailConfirmationService;
    private CepikService cepikService;
    private VehicleService vehicleService;

    public ApplicationController(OrdersService ordersService, CustomersService customersService, DictionariesService dictionariesService, ProductsService productsService, CacheService cacheService, AuthenticatedUser authenticatedUser, GalleryService galleryService, MenuService menuService, AdviserService adviserService, AccountsService accountsService, ApplicationsService applicationsService, ContextConfigsService contextConfigsService, CoursesService coursesService, FilesService filesService, LessonsService lessonsService, BasketService basketService, CourseCustomersService courseCustomersService, ApplicationConfig applicationConfig, EmailService emailService, TemplateEngine templateEngine, InvoicesService invoicesService, CourseOrdersService courseOrdersService, MemesService memesService, AuthorsService authorsService, CourseAttachmentsService courseAttachmentsService, ModulesService modulesService, LessonAttachmentsService lessonAttachmentsService, Environment environment, EmailConfirmationService emailConfirmationService, CepikService cepikService, VehicleService vehicleService) {
        this.ordersService = ordersService;
        this.customersService = customersService;
        this.dictionariesService = dictionariesService;
        this.productsService = productsService;
        this.cacheService = cacheService;
        this.authenticatedUser = authenticatedUser;
        this.galleryService = galleryService;
        this.menuService = menuService;
        this.adviserService = adviserService;
        this.accountsService = accountsService;
        this.applicationsService = applicationsService;
        this.contextConfigsService = contextConfigsService;
        this.coursesService = coursesService;
        this.filesService = filesService;
        this.lessonsService = lessonsService;
        this.basketService = basketService;
        this.courseCustomersService = courseCustomersService;
        this.applicationConfig = applicationConfig;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.invoicesService = invoicesService;
        this.courseOrdersService = courseOrdersService;
        this.memesService = memesService;
        this.authorsService = authorsService;
        this.courseAttachmentsService = courseAttachmentsService;
        this.modulesService = modulesService;
        this.lessonAttachmentsService = lessonAttachmentsService;
        this.environment = environment;
        this.emailConfirmationService = emailConfirmationService;
        this.cepikService = cepikService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam(name = "token") String tokenValue) {
        emailConfirmationService.confirmEmail(tokenValue);
        return "email-confirmed";
    }

    @RequestMapping(value = "/robots.txt")
    public void robots(HttpServletRequest request, HttpServletResponse response) {

        try {
            String fileContent = "";
            response.getWriter().write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/mentoring")
    public String mentoring() {
        return "landing/landing-mentoring";
    }


    @GetMapping({"/memes"})
    public String memesList() {
        return "memes-list";
    }

    @GetMapping({"/job-offers"})
    public String jobOffers() {
        return "job-offers";
    }

    @GetMapping("/itube")
    public String iTube() {
        return "itube";
    }

    @GetMapping({"/meme/{code}"})
    public String memePage(Model model, @PathVariable String code) {
        MemeData meme = memesService.findByCode(code);
        model.addAttribute("meme", new MemeGetResponse(meme));
        return "meme-page";
    }

    @GetMapping({"/java-interview-questions"})
    public String interviewQuestions(Model model) {
        return "front-java-interview-questions";
    }

    @GetMapping({"/java-codility"})
    public String codility(Model model) {
        return "front-java-codility";
    }


    @GetMapping({"/sql-interview-questions"})
    public String sqlQuestions(Model model) {
        return "front-sql-interview-questions";
    }

    @GetMapping({"/methodology-scrum"})
    public String methodologyScrum(Model model) {
        return "front-methodology-scrum";
    }

    @GetMapping({"/methodology-kanban"})
    public String methodologyKanban(Model model) {
        return "front-methodology-kanban";
    }

    @GetMapping({"/methodology-waterfall"})
    public String methodologyWaterfall(Model model) {
        return "front-methodology-waterfall";
    }

    @GetMapping({"/www-links"})
    public String links(Model model) {
        return "front-www-links";
    }

    @GetMapping({"/yt-movies"})
    public String ytMovies(Model model) {
        return "front-yt-movies";
    }

    @GetMapping({"/dictionary"})
    public String dictionary(Model model) {
        return "front-dictionary";
    }

    @GetMapping({"/at-work"})
    public String atWork(Model model) {
        return "front-at-work";
    }

    @GetMapping({"/treatment"})
    public String treatment(Model model) {
        return "front-treatment";
    }

    @GetMapping({"/interview"})
    public String interview(Model model) {
        return "front-interview";
    }

    @GetMapping({"/depression"})
    public String depression(Model model) {
        return "front-depression";
    }
    @GetMapping({"/trauma"})
    public String trauma(Model model) {
        return "front-trauma";
    }

    @GetMapping({"/benefits"})
    public String benefits(Model model) {
        return "front-benefits";
    }

    @GetMapping({"/nice-to-read"})
    public String niceToRead(Model model) {
        return "front-nice-to-read";
    }

    @GetMapping({"/algorithms"})
    public String algorithms(Model model) {
        return "front-algorithms";
    }

    @GetMapping({"/java-test"})
    public String javaTest(Model model) {
        return "front-java-test";
    }

    @GetMapping({"/java-libraries"})
    public String javaLibraries(Model model) {
        return "front-java-libraries";
    }

    @GetMapping({"/java-frameworks"})
    public String javaFrameworks(Model model) {
        return "front-java-frameworks";
    }

    @GetMapping({"/java-architecture"})
    public String javaArchitecture(Model model) {
        return "front-java-architecture";
    }

    @GetMapping({"/java-tasks"})
    public String javaTasks(Model model) {
        return "front-java-tasks";
    }

    @GetMapping({"/java-code"})
    public String javaCode(Model model) {
        return "front-java-code";
    }

    @GetMapping({"/java-tools"})
    public String javaTools(Model model) {
        return "front-java-tools";
    }

    @GetMapping({"/java-links"})
    public String javaLinks(Model model) {
        return "front-java-links";
    }

    @GetMapping({"/java-topics"})
    public String javaTopics(Model model) {
        return "front-java-topics";
    }

    @GetMapping({"/java-script-topics"})
    public String javaScriptTopics(Model model) {
        return "front-java-script-topics";
    }

    @GetMapping({"/java-script-links"})
    public String javaScriptLinks(Model model) {
        return "front-java-script-links";
    }

    @GetMapping({"/cv"})
    public String cv(Model model) {
        return "front-cv";
    }

    @GetMapping({"/creativity"})
    public String creativity(Model model) {
        return "front-creativity";
    }

    @GetMapping({"/stress"})
    public String stress(Model model) {
        return "front-stress";
    }

    @GetMapping({"/meditation"})
    public String meditation(Model model) {
        return "front-meditation";
    }
    @GetMapping({"/form-of-employment"})
    public String formOfEmployment(Model model) {
        return "front-form-of-employment";
    }


    @GetMapping({"/how-to-develop-creativity"})
    public String howToDevelopCreativity(Model model) {
        return "front-how-to-develop-creativity";
    }

    @GetMapping({"/calendar"})
    public String calendar(Model model) {
        return "front-calendar";
    }

    @GetMapping({"/find-job"})
    public String findJob(Model model) {
        return "front-find-job";
    }


    @GetMapping({"/data-format"})
    public String dataFormat(Model model) {
        return "front-data-format";
    }

    @GetMapping({"/no-content"})
    public String noContent(Model model) {
        return "front-no-content";
    }


    @GetMapping({"/reset-password"})
    public String passwordReset(Model model, @RequestParam String token) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @GetMapping({"/forget-password"})
    public String forgetPassword() {
        return "forget-password";
    }

    @GetMapping({"/regulation"})
    public String regulation() {
        return "regulation";
    }

    @GetMapping({"/esp-individual-overview"})
    public String espIndividualOverview() {
        return "esp-individual-overview";
    }

    @GetMapping({"/privacy-policy"})
    public String privacyPolicy() {
        return "privacy-policy";
    }

    @GetMapping({"/rodo"})
    public String rodo() {
        return "rodo";
    }

    @GetMapping(
            value = "/get-image/{name}",
            produces = MediaType.IMAGE_GIF_VALUE
    )
    public @ResponseBody
    byte[] getImage(@PathVariable String name) {
        return filesService.getImage(name);
    }

    @GetMapping(
            value = "/meme/file/{name}",
            produces = MediaType.IMAGE_GIF_VALUE
    )
    public @ResponseBody
    byte[] getMemeFile(@PathVariable(name = "name") String fileName) {
        return filesService.getMemeFile(fileName);
    }

    @GetMapping(
            value = "/get-invoice/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    byte[] getInvoice(@PathVariable Long id) {

        InvoiceData invoice = invoicesService.find(new InvoicesFilter(id, null, false)).get(0);

        if (!courseCustomersService.getLoggedCustomer().getId().equals(invoice.getOrder().getCustomer().getId())) {
            throw new IllegalArgumentException("Access denied");
        }

        return filesService.getInvoice(invoice.getFileName());

    }

    @GetMapping(value = "/download/course/attachment/{id}")
    public @ResponseBody
    byte[] downloadCourseAttachment(@PathVariable Long id) {
        return courseAttachmentsService.getFile(id);
    }

    @GetMapping(value = "/download/lesson/attachment/{lessonAttachmentId}")
    public @ResponseBody
    byte[] downloadLessonAttachment(@PathVariable Long lessonAttachmentId) {
        return lessonAttachmentsService.getFile(lessonAttachmentId);
    }


    @GetMapping({"/payments"})
    public String payments(Model model) {
        model.addAttribute("ordersStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_ORDER_STATUSES));
        model.addAttribute("invoiceTypesDict", dictionariesService.getDictionary(DictionaryType.INVOICE_TYPES));
        return "payments";
    }

    @GetMapping({"/courses"})
    public String courses(Model model) {
        model.addAttribute("pathToGallery", applicationConfig.getCoursePathToGallery());
        return "home";
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        return "home-page";
    }

    @GetMapping({"/buy-our-code"})
    public String buyOurCode(Model model) {
        model.addAttribute("pathToGallery", applicationConfig.getCoursePathToGallery());
        return "buy-our-code-public";
    }

    @GetMapping({"/thanks-for-shopping"})
    public String thanksForShopping() {
        return "thanks-for-shopping";
    }

    @GetMapping({"/register"})
    public String register(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES));
        return "register";
    }

    @GetMapping({"/register-student"})
    public String registerStudent(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES));
        return "register-student";
    }


    @GetMapping({"/register-teacher"})
    public String registerTeacher(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES));
        return "register-teacher";
    }


    @GetMapping({"/basket"})
    public String basket(Model model) {
        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer != null) {
            model.addAttribute("loggedCustomer", new CustomerGetResponse(customer));
        }
        model.addAttribute("pathToGallery", applicationConfig.getCoursePathToGallery());
        model.addAttribute("countriesDict", dictionariesService.getDictionary(DictionaryType.COUNTRIES));
        return "basket";
    }

    @GetMapping({"/registered-successfully"})
    public String registeredSuccessfully() {
        return "registered-successfully";
    }


    @GetMapping({"/registered-student-successfully"})
    public String registeredStudentSuccessfully() {
        return "registered-student-successfully";
    }

    @GetMapping({"/data-changed-successfully"})
    public String dataChangedSuccessfully() {
        return "data-changed-successfully";
    }

    @GetMapping({"/account-settings"})
    public String myAccount(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES));
        model.addAttribute("customer", new CustomerGetResponse(courseCustomersService.getLoggedCustomer()));
        return "my-account";
    }

    @GetMapping({"/my-courses"})
    public String myCourses() {
        return "my-courses";
    }

    @GetMapping({"/my-codes"})
    public String myCodes() {
        return "my-codes";
    }

    @GetMapping({"/my-selections"})
    public String mySelections() {
        return "my-selections";
    }

    @GetMapping({"/selections"})
    public String selections() {
        return "selections";
    }

    @GetMapping({"/check-vin"})
    public String checkVin(Model model) {
        model.addAttribute("voivodeship", cepikService.getVoivodeshipDictionaryTransformed());
        model.addAttribute("brand", cepikService.getBrandDictionaryTransformed());
        model.addAttribute("carKind", cepikService.getVehicleKindDictionaryTransformed());
        model.addAttribute("carOrigin", cepikService.getVehicleOriginDictionaryTransformed());
        model.addAttribute("carProductionMethod", cepikService.getVehicleProductionMethodDictionaryTransformed());
        model.addAttribute("fuelType", cepikService.getVehicleFuelKindDictionaryTransformed());
        return "check-vin";
    }

    @GetMapping({"/vehicle/{id}"})
    public String vehicle(Model model, @PathVariable Long id) {
        model.addAttribute("vehicle", vehicleService.findVehicles(new CarsFilter(id)).get(0));
        return "vehicle";
    }

    @GetMapping({"/selection/{code}"})
    public String selection(@PathVariable String code, Model model) {
        CourseData course = coursesService.find(new CoursesFilter(code)).get(0);
        model.addAttribute("course", new CourseGetResponse(course));
        model.addAttribute("languageFromDict", dictionariesService.getDictionaryElementByCode(course.getLanguage(), DictionaryType.LANGUAGES));
        return "selection";
    }

    @GetMapping({"/watch/course/{courseId}"})
    public String watchCourse(@PathVariable Long courseId, Model model) {

        for (CourseData course : coursesService.findCoursesOwnedByLoggedCustomer()) {
            if (course.getId().equals(courseId)) {

                CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();

                if (loggedCustomer == null) {
                    throw new IllegalArgumentException("Your are logged out. Please log in and retry");
                }

                if (!hasUserCourse(courseId, loggedCustomer)) {
                    throw new IllegalArgumentException("Access denied");
                }

                List<LessonData> lessonsWithoutModule = lessonsService.findWithCustomerActivity(courseId, loggedCustomer, true, null);
                List<ModuleData> modulesInCourse = modulesService.find(new ModulesFilter(courseId, false, ModuleVisibilityStatus.VISIBLE));

                List<ModuleGetResponse> modulesInCourseResponses = new ArrayList<>();

                for (ModuleData moduleInCourse : modulesInCourse) {
                    List<LessonData> lessons = lessonsService.findWithCustomerActivity(courseId, loggedCustomer, null, moduleInCourse.getId());
                    modulesInCourseResponses.add(new ModuleGetResponse(moduleInCourse, lessonsToResponses(lessons)));
                }

                model.addAttribute("lessonsWithoutModules", lessonsToResponses(lessonsWithoutModule));
                model.addAttribute("modules", modulesInCourseResponses);
                model.addAttribute("course", new CourseGetResponse(coursesService.find(new CoursesFilter(courseId)).get(0)));

                return "watch";
            }
        }

        return "error";
    }

    private Boolean hasUserCourse(Long courseId, CustomerData customer) {
        List<CourseData> ownedCourses = coursesService.findCoursesOwnedByCustomer(customer);
        for (CourseData ownedCourse : ownedCourses) {
            if (ownedCourse.getId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

    private List<LessonGetResponse> lessonsToResponses(List<LessonData> lessons) {
        List<LessonGetResponse> list = new ArrayList<>();
        for (LessonData lesson : lessons) {
            list.add(new LessonGetResponse(lesson));
        }
        return list;
    }

    @GetMapping({"/course-details/{id}"})
    public String courseDetails(@PathVariable Long id, Model model) {

        CourseData course = coursesService.find(new CoursesFilter(id)).get(0);

        if (course.getCode() == null || course.getCode().equals("")) {
            model.addAttribute("course", new CourseGetResponse(course));
            model.addAttribute("languageFromDict", dictionariesService.getDictionaryElementByCode(course.getLanguage(), DictionaryType.LANGUAGES));
            return "course-details";
        } else {
            return "redirect:/course/" + course.getCode();
        }

    }

    @GetMapping({"/course/{code}"})
    public String course(@PathVariable String code, Model model) {
        CourseData course = coursesService.find(new CoursesFilter(code)).get(0);
        model.addAttribute("course", new CourseGetResponse(course));
        model.addAttribute("languageFromDict", dictionariesService.getDictionaryElementByCode(course.getLanguage(), DictionaryType.LANGUAGES));
        return "course-details";
    }

    @GetMapping({"/buy-our-code/{code}"})
    public String buyOurCode(@PathVariable String code, Model model) {
        CourseData course = coursesService.find(new CoursesFilter(code)).get(0);
        model.addAttribute("course", new CourseGetResponse(course));
        model.addAttribute("languageFromDict", dictionariesService.getDictionaryElementByCode(course.getLanguage(), DictionaryType.LANGUAGES));
        return "course-details";
    }


    @GetMapping({"/building"})
    public String building(Model model) {
        return "building";
    }

    @GetMapping({"/orders"})
    public String orders(Model model,
                         @RequestParam(name = "first_and_last_name", required = false) String firstAndLastName,
                         @RequestParam(name = "order_date_from", required = false) String orderDateFrom,
                         @RequestParam(name = "order_date_to", required = false) String orderDateTo,
                         @RequestParam(name = "payment_status_id", required = false) Long paymentStatusId,
                         @RequestParam(name = "order_id", required = false) String orderId,
                         @RequestParam(name = "discount_code", required = false) String discountCode,
                         @RequestParam(name = "payment_method_id", required = false) Long paymentMethodId,
                         @RequestParam(name = "driver_id", required = false) Long driverId,
                         @RequestParam(name = "invoice", required = false) String invoice,
                         @RequestParam(name = "delivery_date_from", required = false) String deliveryDateFrom,
                         @RequestParam(name = "delivery_date_to", required = false) String deliveryDateTo,
                         @RequestParam(name = "delivery_method_id", required = false) Long deliveryMethodId,
                         @RequestParam(name = "diet_id", required = false) Long dietId,
                         @RequestParam(name = "order_status_id", required = false) Long orderStatusId,
                         @RequestParam(name = "orders_finishing_in_days", required = false) Long ordersFinishingInDays,
                         @RequestParam(name = "city", required = false) String city,
                         @RequestParam(name = "marked_as_paid_date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate markedAsPaidDateFrom,
                         @RequestParam(name = "marked_as_paid_date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate markedAsPaidDateTo,
                         @RequestParam(name = "page", required = false) Long page,
                         @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize
    ) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ORDERS})) {
            return "denied";
        }
        OrdersFilter filter = prepareFilterForOrders(
                firstAndLastName,
                orderDateFrom,
                orderDateTo,
                paymentStatusId,
                orderId,
                discountCode,
                paymentMethodId,
                driverId,
                "1".equalsIgnoreCase(invoice),
                deliveryDateFrom,
                deliveryDateTo,
                deliveryMethodId,
                dietId,
                orderStatusId,
                ordersFinishingInDays,
                city,
                markedAsPaidDateFrom,
                markedAsPaidDateTo
        );
        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
        model.addAttribute("drivers", dictionariesService.getDictionary(DictionaryType.DRIVERS, Language.PL));
        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("cities", dictionariesService.getDictionary(DictionaryType.CITIES, Language.PL));
        model.addAttribute("yesNo", dictionariesService.getDictionary(DictionaryType.YES_NO, Language.PL));

        page = initPageWhenNotSet(page, 1);

        model.addAttribute("selectedPage", page);
        model.addAttribute("selectedPageSize", pageSize);

        return "orders";
    }

    private OrdersFilter prepareFilterForOrders(String firstAndLastName,
                                                String orderDateFrom,
                                                String orderDateTo,
                                                Long paymentStatusId,
                                                String orderId,
                                                String discountCode,
                                                Long paymentMethodId,
                                                Long driverId,
                                                Boolean invoice,
                                                String deliveryDateFrom,
                                                String deliveryDateTo,
                                                Long deliveryMethodId,
                                                Long dietId,
                                                Long orderStatusId,
                                                Long ordersFinishingInDays,
                                                String city,
                                                LocalDate markedAsPaidDateFrom,
                                                LocalDate markedAsPaidDateTo) {
        LocalDate ordDateFrom = null;
        LocalDate ordDateTo = null;
        if (orderDateFrom == null || orderDateFrom.isEmpty()) {
            ordDateFrom = LocalDate.now();
        } else {
            ordDateFrom = LocalDate.parse(orderDateFrom);
        }
        if (orderDateTo != null && !orderDateTo.isEmpty()) {
            ordDateTo = LocalDate.parse(orderDateFrom);
        } else {
            ordDateTo = null;
        }
        LocalDate deliveryDtFrom = null;
        if (deliveryDateFrom != null && !deliveryDateFrom.isEmpty()) {
            deliveryDtFrom = LocalDate.parse(deliveryDateFrom);
        }
        LocalDate deliveryDtTo = null;
        if (deliveryDateTo != null && !deliveryDateTo.isEmpty()) {
            deliveryDtTo = LocalDate.parse(deliveryDateTo);
        }
        return new OrdersFilter(ordDateFrom, ordDateTo, firstAndLastName, deliveryDtFrom, deliveryDtTo, paymentStatusId, discountCode, paymentMethodId, driverId, deliveryMethodId, dietId, orderStatusId, ordersFinishingInDays, city, markedAsPaidDateFrom, markedAsPaidDateTo, invoice, orderId, null);
    }

    @GetMapping({"/order"})
    public String order(@RequestParam(name = "id") Long id, @RequestParam(name = "read_only", required = false, defaultValue = "false") boolean readOnly, Model model, HttpServletRequest request) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ORDERS})) {
            return "denied";
        }
        long startTime = System.currentTimeMillis();
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("yesNo", dictionariesService.getDictionary(DictionaryType.YES_NO, Language.PL));
        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
        model.addAttribute("paymentPaymentMethods", dictionariesService.getDictionary(DictionaryType.PAYMENT_PAYMENT_METHODS, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));
        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
        model.addAttribute("cities", dictionariesService.getDictionary(DictionaryType.CITIES, Language.PL));
        model.addAttribute("weekendOptions", dictionariesService.getDictionary(DictionaryType.WEEKEND_OPTIONS, Language.PL));

        OrderDetailsData data = ordersService.getOrder(id);
        model.addAttribute("data", data);
        model.addAttribute("customer", data.getCustomerForOrder());
        model.addAttribute("weekendAddress", data.getOrderWeekendAddress());
        model.addAttribute("invoice", data.getOrderInvoice());

        model.addAttribute("paymentResult", ordersService.getPaymentResultForOrder(id, data.getOrderBasketSum()));
        model.addAttribute("productResult", ordersService.getProductResultForOrder(id, data.getOrderBasketSumNo(), data.getOrderBasketSum()));
        model.addAttribute("deliveries", ordersService.getDeliveryDataForOrder(id, data));
        model.addAttribute("changes", ordersService.getChangesForOrder(id));
        model.addAttribute("deliveryChanges", ordersService.getDeliveryChangesForOrder(id));
        model.addAttribute("emails", ordersService.findOrderSentEmails(id));
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "ApplicationController.order", timeTaken);

        if (!readOnly && !authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ORDERS})) {
            readOnly = true;
        }
        model.addAttribute("readOnly", readOnly);

//        System.out.println("referrer: " + request.getHeader("referrer"));
//        System.out.println("getPathInfo: " + request.getPathInfo());
//        System.out.println("getContextPath: " + request.getContextPath());
//        System.out.println("getPathTranslated: " + request.getPathTranslated());
//        System.out.println("getRequestURI: " + request.getRequestURI());
//        System.out.println("getServletPath: " + request.getServletPath());
//        System.out.println("referrer: " + request.getHeader("X-Forwarded-Referrer"));


        return "order";
    }

    @GetMapping({"/orders-short"})
    public String ordersShort(Model model,
                              @RequestParam(name = "first_name", required = false) String firstName,
                              @RequestParam(name = "last_name", required = false) String lastName,
                              @RequestParam(name = "order_id", required = false) String orderId,
                              @RequestParam(name = "page", required = false) Long page,
                              @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize
    ) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.SIMPLIFIED_ORDERS})) {
            return "denied";
        }
        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("yesNo", dictionariesService.getDictionary(DictionaryType.YES_NO, Language.PL));

        page = initPageWhenNotSet(page, 1);

        model.addAttribute("list", ordersService.findOrdersShort(new OrdersFilter(firstName, lastName, orderId), page, pageSize));
        model.addAttribute("selectedPage", page);
        model.addAttribute("selectedPageSize", pageSize);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("orderId", orderId);


        return "orders-short";
    }


    @GetMapping({"/products-demand"})
    public String productsDemand(Model model,
                                 @RequestParam(name = "dateAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAt,
                                 @RequestParam(name = "page", required = false) Long page,
                                 @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize
    ) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.DELIVERY_REQUEST})) {
            return "denied";
        }
        if (dateAt == null) {
            dateAt = LocalDate.now();
        }
        page = initPageWhenNotSet(page, 1);

        ProductDemandResultData result = productsService.findProductDemandsWithSum(dateAt, page, pageSize);
        model.addAttribute("list", result.getProducts());
        model.addAttribute("sum", result.getSum());

        model.addAttribute("selectedDate", dateAt.format(DateTimeFormatter.ISO_DATE)); //TODO if no parameter - today's date
        model.addAttribute("selectedPage", page);
        model.addAttribute("selectedPageSize", pageSize);
        return "products-demand";
    }

    @GetMapping({"/test-sets"})
    public String testSets(Model model) {
        return "test-sets";
    }

    @GetMapping({"/statistics"})
    public String statistics(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.DELIVERY_VALUE})) {
            return "denied";
        }
        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
        model.addAttribute("drivers", dictionariesService.getDictionary(DictionaryType.DRIVERS, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now());
        model.addAttribute("defaultEndDate", LocalDate.now());

        return "statistics";
    }

    @GetMapping({"/menu-preview"})
    public String menuPreview(Model model
    ) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.MENU_PREVIEW})) {
            return "denied";
        }
//        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
//        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
//        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
//        model.addAttribute("drivers", dictionariesService.getDictionary(DictionaryType.DRIVERS, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now());
        model.addAttribute("defaultEndDate", LocalDate.now().plusDays(6));

        return "menu-preview";
    }

    @GetMapping({"/excel-menu-preview"})
    public ResponseEntity<InputStreamResource> menuPreviewExcel(
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "diet_id") Long dietId,
            @RequestParam(name = "kind_id") Long kindId) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.MENU_PREVIEW})) {
            return null;
        }
        ExcelMenuPreviewData excel = null;
        try {
            long startTime = System.currentTimeMillis();
            excel = menuService.generateMenuPreviewExcel(from, to, dietId, kindId);
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("Time Taken by {} is {}", "generateMenuPreviewExcel", timeTaken);
        } catch (IOException e) {
            log.error("Error during excel generation from: " + from + " to: " + to + " dietId: " + dietId + " kindId: " + kindId);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + excel.getFileName())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(excel.getSize()) //
                .body(new InputStreamResource(excel.getData()));
    }

    @GetMapping({"/gallery"})
    public String gallery(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.GALLERY})) {
            return "denied";
        }
        useModelForGallery(model);
        return "gallery";
    }

    private void useModelForGallery(Model model) {
        model.addAttribute("paymentStatuses", dictionariesService.getDictionary(DictionaryType.PAYMENT_STATUSES, Language.PL));
        model.addAttribute("paymentMethods", dictionariesService.getDictionary(DictionaryType.ORDER_PAYMENT_METHODS, Language.PL));
        model.addAttribute("shipmentTypes", dictionariesService.getDictionary(DictionaryType.SHIPMENT_TYPES, Language.PL));
        model.addAttribute("drivers", dictionariesService.getDictionary(DictionaryType.DRIVERS, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now());
        model.addAttribute("defaultEndDate", LocalDate.now());
    }

    @GetMapping({"/gallery-image"})
    public String galleryImage(Model model, @RequestParam(name = "id", required = false) Long id) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.GALLERY})) {
            return "denied";
        }
        prepareModelForImage(model, id);
        galleryService.clean();
        return "gallery-image";
    }

    @GetMapping({"/customers-new"})
    public String newCustomers(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.CUSTOMERS_STATS})) {
            return "denied";
        }
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now().minusMonths(1));
        model.addAttribute("defaultEndDate", LocalDate.now());
        return "customers-new";
    }

    @GetMapping({"/customer-groups"})
    public String customerGroups(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.CUSTOMERS_GROUPS})) {
            return "denied";
        }

        model.addAttribute("groupStatuses", dictionariesService.getDictionary(DictionaryType.CUSTOMER_GROUP_STATUSES, Language.PL));

        return "customer-groups";
    }

    @GetMapping({"/customer"})
    public String customer(@RequestParam(name = "id") Long customerId, Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.CUSTOMERS})) {
            return "denied";
        }
        model.addAttribute("customerId", customerId);
        model.addAttribute("customerGroups", dictionariesService.getDictionary(DictionaryType.CUSTOMER_GROUPS, Language.PL));
        model.addAttribute("customerStatuses", dictionariesService.getDictionary(DictionaryType.CUSTOMER_STATUSES, Language.PL));
        model.addAttribute("cities", dictionariesService.getDictionary(DictionaryType.CITIES, Language.PL));
        model.addAttribute("yesNo", dictionariesService.getDictionary(DictionaryType.YES_NO, Language.PL));
        model.addAttribute("customersLastOrderId", ordersService.getCustomersLastOrderId(customerId));
        return "customer";
    }


    @GetMapping({"/login"})
    public String login() {
        return "login";
    }


    @GetMapping({"/logout"})
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("logout", true);
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        cacheService.invalidateDictionaries();
        log.debug(CacheType.DICTIONARIES + " cache invalidated");
        cacheService.invalidateMenu();
        log.debug(CacheType.MENU + " cache invalidated");
        return "login";
    }

    @PostMapping({"/upload"})
    public String upload(Model model, @RequestParam(name = "id", required = false) Long id, @RequestParam("file") MultipartFile file) {
        String error = null;
        UploadResult result = null;
        try {
            result = galleryService.storeFile(id, file);
        } catch (RuntimeException | IOException ex) {
            error = ex.getMessage();
        }
        prepareModelForImage(model, id, error, result);
        System.out.println(error);
        return "gallery-image";
    }

    @GetMapping({"/customers"})
    public String customers(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.CUSTOMERS})) {
            return "denied";
        }

        model.addAttribute("customerTypes", dictionariesService.getDictionary(DictionaryType.CUSTOMER_TYPES, Language.PL));
        model.addAttribute("customerGroups", dictionariesService.getDictionary(DictionaryType.CUSTOMER_GROUPS, Language.PL));
        model.addAttribute("customerStatuses", dictionariesService.getDictionary(DictionaryType.CUSTOMER_STATUSES, Language.PL));

        return "customers";
    }

    @GetMapping({"/allergens"})
    public String allergens(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.DIETETICS_SETTINGS_ALLERGEN})) {
            return "denied";
        }
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now().minusMonths(1));
        model.addAttribute("defaultEndDate", LocalDate.now());
        return "allergens";
    }

    @GetMapping({"/products-types"})
    public String productsTypes(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.DIETETICS_PRODUCT_TYPES})) {
            return "denied";
            // todo poprawne dane
        }
        model.addAttribute("orderStatuses", dictionariesService.getDictionary(DictionaryType.ORDER_STATUSES, Language.PL));
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));

        model.addAttribute("defaultStartDate", LocalDate.now().minusMonths(1));
        model.addAttribute("defaultEndDate", LocalDate.now());
        return "products-types";
    }


    @GetMapping({"/kurs-debugowanie-java-w-intellij"})
    public String courseDebugingJavaInIntellij(Model model) {
        return "landing/landing-debugging-java";
    }

    @GetMapping({"/kurs-flutter-od-podstaw-aplikacje-mobilne"})
    public String courseFlutter(Model model) {
        return "landing/landing-flutter-basics";
    }

    @GetMapping({"/kurs-javascript-od-podstaw"})
    public String courseJavascript(Model model) {
        return "landing/landing-javascript-basics";
    }

    @GetMapping({"/kurs-html-od-podstaw"})
    public String courseHtml(Model model) {
        return "landing/landing-html-basics";
    }

    @GetMapping({"/kurs-javascript-dom"})
    public String courseJavascriptDom(Model model) {
        return "landing/landing-javascript-dom";
    }

    @GetMapping({"/kurs-sql-i-mysql"})
    public String courseSql(Model model) {
        return "landing/landing-mysql";
    }


    private void prepareModelForImage(Model model, Long id) {
        prepareModelForImage(model, id, null, null);
    }

    private void prepareModelForImage(Model model, Long id, String error, UploadResult result) {
        model.addAttribute("diets", dictionariesService.getDictionary(DictionaryType.DIETS, Language.PL));
        model.addAttribute("id", id);
        model.addAttribute("error", error);
        model.addAttribute("uploadedFileName", result != null ? result.getFileName() : null);
        model.addAttribute("imageKind", dictionariesService.getDictionary(DictionaryType.GALLERY_IMAGE_KIND, Language.PL));
        model.addAttribute("data", new ImageData(""));
        if (isModificationMode(id)) {
            ImageData image = galleryService.getImage(id);
            model.addAttribute("data", image);
            if (result == null) {
                model.addAttribute("uploadedFileName", image.getFileName());
            }
        }
    }

    private boolean isModificationMode(Long id) {
        return id != null;
    }

    private long initPageWhenNotSet(@RequestParam(name = "page", required = false) Long page, int i) {
        return page != null ? page : i;
    }
}
