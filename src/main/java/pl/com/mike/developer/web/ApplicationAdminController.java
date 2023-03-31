package pl.com.mike.developer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import pl.com.mike.developer.api.courseplatform.*;
import pl.com.mike.developer.auth.AuthenticatedUser;
import pl.com.mike.developer.auth.Permissions;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.ImageData;
import pl.com.mike.developer.domain.InvoiceData;
import pl.com.mike.developer.domain.courseplatform.*;
import pl.com.mike.developer.domain.itube.ITubeFilter;
import pl.com.mike.developer.domain.itube.ITubeGetResponseAdmin;
import pl.com.mike.developer.logic.Language;
import pl.com.mike.developer.logic.*;
import pl.com.mike.developer.logic.courseplatform.*;
import pl.com.mike.developer.logic.itube.ITubeService;

@Controller
public class ApplicationAdminController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationAdminController.class);
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
    private CustomerGroupsService customerGroupsService;
    private  CustomerToGroupService customerToGroupService;
    private ITubeService iTubeService;

    public ApplicationAdminController(CustomersService customersService, DictionariesService dictionariesService, CacheService cacheService,
                                      AuthenticatedUser authenticatedUser, CoursesService coursesService, FilesService filesService,
                                      LessonsService lessonsService, BasketService basketService, CourseCustomersService courseCustomersService, ApplicationConfig applicationConfig,
                                      EmailService emailService, TemplateEngine templateEngine, InvoicesService invoicesService, CourseOrdersService courseOrdersService,
                                      MemesService memesService, AuthorsService authorsService, CourseAttachmentsService courseAttachmentsService, ModulesService modulesService,
                                      LessonAttachmentsService lessonAttachmentsService, Environment environment, EmailConfirmationService emailConfirmationService,
                                      CustomerGroupsService customerGroupsService, CustomerToGroupService customerToGroupService, ITubeService iTubeService) {
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
        this.customerGroupsService = customerGroupsService;
        this.customerToGroupService = customerToGroupService;
        this.iTubeService = iTubeService;
    }

    @GetMapping("/admin/lesson-comments")
    public String lessonComments(Model model) {
        model.addAttribute("lessonCommentStatusesDict", dictionariesService.getDictionary(DictionaryType.LESSON_COMMENT_STATUSES, LanguagesUtil.getCurrentLanguage()));
        return "lesson-comments";
    }

    @GetMapping("/admin/course-comments")
    public String courseComments(Model model) {
        model.addAttribute("courseCommentVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_COMMENT_VISIBILITY_STATUSES, LanguagesUtil.getCurrentLanguage()));
        return "course-comments";
    }


    @GetMapping({"/admin/memes"})
    public String memes(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "memes";
    }

    @GetMapping({"/admin/meme/{id}"})
    public String meme(Model model, @PathVariable Long id) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        model.addAttribute("meme", new MemeGetResponse(memesService.find(new MemesFilter(id)).get(0)));
        return "meme";
    }


    @GetMapping(
            value = "/admin/get-invoice/{id}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    byte[] getInvoiceAdmin(@PathVariable Long id) {
        InvoiceData invoice = invoicesService.find(new InvoicesFilter(id, null)).get(0);
        return filesService.getInvoice(invoice.getFileName());
    }

    @GetMapping({"/admin"})
    public String admin() {
        return "admin";
    }


    @GetMapping({"/admin/procedure/course-return"})
    public String procedureCourseReturn() {
        return "procedure-course-return";
    }

    @GetMapping({"/admin/procedure/issue-invoice"})
    public String procedureIssueInvoice() {
        return "procedure-issue-invoice";
    }


    @GetMapping({"/admin/selections"})
    public String adminSelections(Model model) {
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, Language.US));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, Language.US));
        return "selections-admin";
    }

    @GetMapping({"/admin/selection/{id}"})
    public String adminSelection(Model model, @PathVariable Long id) {

        if (!coursesService.hasLoggedCustomerPermissionsToCourseManagement(id)) {
            throw new IllegalArgumentException("Access denied");
        }

        model.addAttribute("course", new CourseGetResponse(coursesService.find(new CoursesFilter(id)).get(0)));
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, Language.US));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, Language.US));
        model.addAttribute("lessonVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.LESSON_VISIBILITY_STATUSES, Language.US));
        model.addAttribute("courseSaleStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_SALE_STATUSES, Language.US));
        model.addAttribute("movieLinkTypesDict", dictionariesService.getDictionary(DictionaryType.MOVIE_LINK_TYPES, Language.US));

        return "selection-admin";

    }

    @PostMapping("/admin/update/image/course")
    public String uploadImage(@RequestParam(name = "id") Long courseId, @RequestParam("imageFile") MultipartFile imageFile) {
        coursesService.updateAndSaveImage(courseId, imageFile);
        return "redirect:/admin/course/" + courseId;
    }

    @PostMapping("/admin/upload-invoice")
    public String uploadInvoice(@RequestParam(name = "orderId") Long orderId,
                                @RequestParam(name = "invoiceFile") MultipartFile invoiceFile,
                                @RequestParam(name = "invoiceType") String invoiceType,
                                @RequestParam(name = "invoiceNumber") String invoiceNumber
    ) {
        invoicesService.addInvoiceToOrder(orderId, invoiceFile, new InvoiceData(invoiceType, invoiceNumber));
        return "redirect:/admin/order/" + orderId;
    }

    @GetMapping({"/admin/courses"})
    public String adminCourses(Model model) {
        Language currentLang = LanguagesUtil.getCurrentLanguage();
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, currentLang));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, currentLang));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, currentLang));
        return "courses";
    }

    @GetMapping({"/admin/buy-our-code"})
    public String buyOurCodeAdmin(Model model) {
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, Language.US));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, Language.US));
        return "buy-our-code";
    }

    @GetMapping({"/admin/orders"})
    public String orders(Model model) {
        model.addAttribute("ordersStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_ORDER_STATUSES, Language.US));
        model.addAttribute("yesNoDict", dictionariesService.getDictionary(DictionaryType.YES_NO, Language.US));
        return "course-orders";
    }

    @GetMapping({"/admin/authors"})
    public String authors() {
        return "authors";
    }
    @GetMapping({"/admin/customer-groups"})
    public String customerGroups() {
        return "crs-customer-groups";
    }

    @GetMapping({"/admin/statistics/summary"})
    public String statistics() {
        return "admin-statistics-summary";
    }

    @GetMapping({"/admin/marketing/newsletter"})
    public String newsletter() {
        return "admin-marketing-newsletter";
    }

    @GetMapping({"/admin/notifications/send"})
    public String notificationsSending(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "admin-notifications-send";
    }

    @GetMapping({"/admin/statistics/top-selling-money"})
    public String topSellingMoney() {
        return "admin-statistics-top-selling-money";
    }

    @GetMapping({"/admin/customers"})
    public String adminCustomers(Model model) {
        Language currentLang = LanguagesUtil.getCurrentLanguage();
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, currentLang));
        model.addAttribute("yesNoDict", dictionariesService.getDictionary(DictionaryType.YES_NO, currentLang));
        model.addAttribute("authoritiesDict", dictionariesService.getDictionary(DictionaryType.CUSTOMER_AUTHORITIES, currentLang));
        model.addAttribute("customerGroups", customerGroupsService.find(new CustomerGroupsFilter(false)));
        return "customers-admin";
    }

    @GetMapping({"/admin/customer/{id}"})
    public String customer(Model model, @PathVariable Long id) {
        Language currentLang = LanguagesUtil.getCurrentLanguage();
        model.addAttribute("customer", new CustomerGetResponse(courseCustomersService.findWithDetails(new CustomersFilter(id)).get(0)));
        model.addAttribute("authoritiesDict", dictionariesService.getDictionary(DictionaryType.CUSTOMER_AUTHORITIES, currentLang));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, currentLang));
        model.addAttribute("yesNoDict", dictionariesService.getDictionary(DictionaryType.YES_NO, currentLang));
        model.addAttribute("invoiceTypesDict", dictionariesService.getDictionary(DictionaryType.INVOICE_TYPES, currentLang));
        model.addAttribute("countriesDict", dictionariesService.getDictionary(DictionaryType.COUNTRIES, currentLang));
        model.addAttribute("customerGroups", customerGroupsService.find(new CustomerGroupsFilter(false)));
        return "customer-admin";
    }

    @GetMapping({"/admin/statistics/new-customers"})
    public String newCustomers() {
        return "admin-statistics-new-customers";
    }

    @GetMapping({"/admin/statistics/visited-landings-count"})
    public String visitedLandingsCount() {
        return "admin-statistics-visited-landings-count";
    }

    @GetMapping({"/admin/statistics/courses-completion"})
    public String courseCompletion() {
        return "admin-statistics-completion";
    }

    @GetMapping({"/admin/author/{id}"})
    public String author(Model model, @PathVariable Long id) {
        model.addAttribute("author", new AuthorGetResponse(authorsService.find(new AuthorsFilter(id)).get(0)));
        return "author";
    }
    @GetMapping({"/admin/customer-group/{id}"})
    public String customerGroup(Model model, @PathVariable Long id) {
        model.addAttribute("customerGroup", new CustomerGroupGetResponse(customerGroupsService.find(new CustomerGroupsFilter(id)).get(0)));
        return "crs-customer-group";
    }

    @GetMapping({"/admin/order/{id}"})
    public String order(Model model, @PathVariable Long id) {
        model.addAttribute("order", new CourseOrderAdminGetResponse(courseOrdersService.find(new CourseOrdersFilter(id, null)).get(0)));
        model.addAttribute("billingTypesDict", dictionariesService.getDictionary(DictionaryType.BILLING_TYPES, Language.US));
        model.addAttribute("orderStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_ORDER_STATUSES, Language.US));
        model.addAttribute("invoiceTypesDict", dictionariesService.getDictionary(DictionaryType.INVOICE_TYPES, Language.US));
        return "course-order";
    }

    @GetMapping({"/admin/course/{id}"})
    public String course(@PathVariable Long id, Model model) {

        if (!coursesService.hasLoggedCustomerPermissionsToCourseManagement(id)) {
            throw new IllegalArgumentException("Access denied");
        }

        Language lang = LanguagesUtil.getCurrentLanguage();

        model.addAttribute("course", new CourseGetResponse(coursesService.find(new CoursesFilter(id)).get(0)));
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, lang));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, lang));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, lang));
        model.addAttribute("lessonVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.LESSON_VISIBILITY_STATUSES, lang));
        model.addAttribute("moduleVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.MODULE_VISIBILITY_STATUSES, lang));
        model.addAttribute("courseSaleStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_SALE_STATUSES, lang));
        model.addAttribute("movieLinkTypesDict", dictionariesService.getDictionary(DictionaryType.MOVIE_LINK_TYPES, lang));
        return "course";
    }

    @GetMapping({"/admin/buy-our-code/details/{id}"})
    public String buyOurCodeDetails(@PathVariable Long id, Model model) {

        if (!coursesService.hasLoggedCustomerPermissionsToCourseManagement(id)) {
            throw new IllegalArgumentException("Access denied");
        }

        model.addAttribute("course", new CourseGetResponse(coursesService.find(new CoursesFilter(id)).get(0)));
        model.addAttribute("authorsDict", dictionariesService.getDictionary(DictionaryType.AUTHORS, Language.US));
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        model.addAttribute("courseVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_VISIBILITY_STATUSES, Language.US));
        model.addAttribute("lessonVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.LESSON_VISIBILITY_STATUSES, Language.US));
        model.addAttribute("courseSaleStatusesDict", dictionariesService.getDictionary(DictionaryType.COURSE_SALE_STATUSES, Language.US));
        model.addAttribute("movieLinkTypesDict", dictionariesService.getDictionary(DictionaryType.MOVIE_LINK_TYPES, Language.US));
        return "buy-our-code-details";
    }

    @GetMapping({"/admin/module/{id}"})
    public String module(@PathVariable Long id, Model model) {
        ModuleData module = modulesService.get(id);
        model.addAttribute("moduleVisibilityStatusesDict", dictionariesService.getDictionary(DictionaryType.MODULE_VISIBILITY_STATUSES, LanguagesUtil.getCurrentLanguage()));
        model.addAttribute("module", new ModuleGetResponse(module));
        return "module";
    }


    @GetMapping({"/admin/login"})
    public String adminLogin() {
        return "login-admin";
    }


    @GetMapping({"/admin/advices"})
    public String advices() {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ADVICES})) {
//            return "denied";
//        }
        return "advices";
    }

    @GetMapping({"/admin/triggered-advices"})
    public String triggeredAdvices() {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_TRIGGERED_ADVICES})) {
//            return "denied";
//        }

        return "triggered-advices";
    }

//    @GetMapping({"/admin/triggered-advice/{id}"})
//    public String triggeredAdvice(@PathVariable Long id, Model model) {
////        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_TRIGGERED_ADVICES})) {
////            return "denied";
////        }
//        model.addAttribute("triggeredAdvice", new TriggeredAdviceGetResponse(adviserService.getTriggeredAdvice(id)));
//        return "triggered-advice";
//    }
//
//    @GetMapping({"/admin/advice/{id}"})
//    public String advice(@PathVariable Long id, Model model) {
//
////        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ADVICES})) {
////            return "denied";
////        }
//
//        model.addAttribute("advice", new AdviceGetResponse(adviserService.getAdvice(id)));
//
//        return "advice";
//    }

    @GetMapping({"/admin/accounts"})
    public String accounts() {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ACCOUNTS})) {
            return "denied";
        }

        return "accounts";
    }

//    @GetMapping({"/admin/account/{id}"})
//    public String account(@PathVariable Long id, Model model) {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ACCOUNTS})) {
//            return "denied";
//        }
//
//        model.addAttribute("account", accountToResponses(accountsService.get(id)));
//
//        return "account";
//    }

    @GetMapping({"/admin/applications"})
    public String applications() {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_APPLICATIONS})) {
            return "denied";
        }

        return "applications";
    }

//    @GetMapping({"/admin/application/{id}"})
//    public String application(@PathVariable Long id, Model model) {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_APPLICATIONS})) {
//            return "denied";
//        }
//
//        model.addAttribute("app", applicationToResponse(applicationsService.get(id)));
//
//        return "application";
//    }

    @GetMapping({"/admin/context-variables"})
    public String contextVariables() {

        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_CONTEXT_VARIABLES})) {
            return "denied";
        }

        return "context-variables";
    }

    @GetMapping({"/admin/context-configs"})
    public String contextConfigs() {

        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_CONTEXT_CONFIGS})) {
            return "denied";
        }

        return "context-configs";
    }

//    @GetMapping({"/admin/context-config/{id}"})
//    public String contextConfig(@PathVariable Long id, Model model) {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_CONTEXT_CONFIGS})) {
//            return "denied";
//        }
//
//        model.addAttribute("contextConfig", contextConfigToResponse(contextConfigsService.get(id)));
//
//        return "context-config";
//    }

    @GetMapping({"/admin/the-newest"})
    public String newestAdvices(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.LIFE_ADVISER_COMMON})) {
            return "denied";
        }

        return "the-newest";
    }

    @GetMapping({"/admin/likes"})
    public String yourLikes(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.LIFE_ADVISER_COMMON})) {
            return "denied";
        }

        return "likes";
    }

    @GetMapping({"/admin/not-liked"})
    public String notLiked(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.LIFE_ADVISER_COMMON})) {
            return "denied";
        }

        return "not-liked";
    }

    @GetMapping({"/admin/my-categories"})
    public String myCategories(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.LIFE_ADVISER_COMMON})) {
            return "denied";
        }

        return "my-categories";
    }

    @GetMapping({"/admin/market"})
    public String market(Model model) {
        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.LIFE_ADVISER_COMMON})) {
            return "denied";
        }

        return "market";
    }

    @GetMapping({"/admin/advice-categories"})
    public String categories(Model model) {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ADVICE_CATEGORIES})) {
//            return "denied";
//        }

        return "advice-categories";
    }

    @GetMapping({"/admin/suggestion"})
    public String suggestion(Model model) {
        if (!authenticatedUser.hasAnyPermission(Permissions.LIFE_ADVISER_COMMON)) {
            return "denied";
        }

        return "suggestion";
    }

    @GetMapping({"/admin/options"})
    public String options(Model model) {
        if (!authenticatedUser.hasAnyPermission(Permissions.LIFE_ADVISER_COMMON)) {
            return "denied";
        }

        return "options";
    }

    @GetMapping({"/admin/job-offers"})
    public String jobOffer(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "job-offers-page";
    }

    @GetMapping({"/admin/contacts/people"})
    public String contactsPeople(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "contacts-people";
    }

    @GetMapping({"/admin/itubes"})
    public String iTube(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.PL));
        return "itubes";
    }

    @GetMapping({"/admin/itube-page/{id}"})
    public String iTube(@PathVariable Long id, Model model) {
        ITubeGetResponseAdmin iTubeGetResponseAdmin = ConverterToResponsesUtil.iTubesToResponsesAdmin(iTubeService.find(new ITubeFilter(id))).get(0);
        model.addAttribute("itube", new ITubeGetResponseAdmin(
                iTubeGetResponseAdmin.getId(),
                iTubeGetResponseAdmin.getTitlePl(),
                iTubeGetResponseAdmin.getTitleEn(),
                iTubeGetResponseAdmin.getKeywords(),
                iTubeGetResponseAdmin.getLink(),
                iTubeGetResponseAdmin.getCreateDateTime()
        ));
        return "itube-page";
    }

    @GetMapping({"/admin/statistics/trace-per-day"})
    public String tracePerDay(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "admin-statistics-trace-per-day";
    }

    @GetMapping({"/admin/statistics/trace-per-month"})
    public String tracePerMonth(Model model) {
        model.addAttribute("languagesDict", dictionariesService.getDictionary(DictionaryType.LANGUAGES, Language.US));
        return "admin-statistics-trace-per-month";
    }

//    private ContextConfigGetResponse contextConfigToResponse(ContextConfigData data) {
//        return new ContextConfigGetResponse(data.getId(), data.getApplicationId(), data.getContext(), data.getName(), data.getType(), data.getValue());
//    }
//
//    private ApplicationGetResponse applicationToResponse(ApplicationData data) {
//        return new ApplicationGetResponse(data.getId(), data.getApplicationId(), data.getDescription(), data.getSecretKey());
//    }
//
//    private AccountGetResponse accountToResponses(AccountData data) {
//        return new AccountGetResponse(data.getId(), data.getName());
//    }

    private boolean isModificationMode(Long id) {
        return id != null;
    }

}
