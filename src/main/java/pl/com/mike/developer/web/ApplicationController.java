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
    private CustomersService customersService;
    private DictionariesService dictionariesService;
    private CacheService cacheService;
    private AuthenticatedUser authenticatedUser;
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

    public ApplicationController( CustomersService customersService, DictionariesService dictionariesService, CacheService cacheService, AuthenticatedUser authenticatedUser, CoursesService coursesService, FilesService filesService, LessonsService lessonsService, BasketService basketService, CourseCustomersService courseCustomersService, ApplicationConfig applicationConfig, EmailService emailService, TemplateEngine templateEngine, InvoicesService invoicesService, CourseOrdersService courseOrdersService, MemesService memesService, AuthorsService authorsService, CourseAttachmentsService courseAttachmentsService, ModulesService modulesService, LessonAttachmentsService lessonAttachmentsService, Environment environment, EmailConfirmationService emailConfirmationService, CepikService cepikService, VehicleService vehicleService) {
        this.customersService = customersService;
        this.dictionariesService = dictionariesService;
        this.cacheService = cacheService;
        this.authenticatedUser = authenticatedUser;
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

//        model.addAttribute("list", ordersService.findOrdersShort(new OrdersFilter(firstName, lastName, orderId), page, pageSize));
        model.addAttribute("selectedPage", page);
        model.addAttribute("selectedPageSize", pageSize);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("orderId", orderId);


        return "orders-short";
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
//        model.addAttribute("customersLastOrderId", ordersService.getCustomersLastOrderId(customerId));
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

    private long initPageWhenNotSet(@RequestParam(name = "page", required = false) Long page, int i) {
        return page != null ? page : i;
    }
}
