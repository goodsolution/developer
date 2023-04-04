package pl.com.mike.developer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import pl.com.mike.developer.api.courseplatform.AuthorGetResponse;
import pl.com.mike.developer.api.courseplatform.MemeGetResponse;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.AuthorsFilter;
import pl.com.mike.developer.domain.courseplatform.MemesFilter;
import pl.com.mike.developer.domain.itube.ITubeFilter;
import pl.com.mike.developer.domain.itube.ITubeGetResponseAdmin;
import pl.com.mike.developer.logic.Language;
import pl.com.mike.developer.logic.*;
import pl.com.mike.developer.logic.courseplatform.*;
import pl.com.mike.developer.logic.itube.ITubeService;

@Controller
public class ApplicationAdminController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationAdminController.class);
    private DictionariesService dictionariesService;

    private MemesService memesService;
    private AuthorsService authorsService;

    private ITubeService iTubeService;

    public ApplicationAdminController(DictionariesService dictionariesService, CacheService cacheService,
                                      FilesService filesService,
                                      ApplicationConfig applicationConfig,
                                      TemplateEngine templateEngine,
                                      MemesService memesService, AuthorsService authorsService, ModulesService modulesService,
                                      Environment environment,
                                      ITubeService iTubeService) {
        this.dictionariesService = dictionariesService;
        this.memesService = memesService;
        this.authorsService = authorsService;
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


    @GetMapping({"/admin/advice-categories"})
    public String categories(Model model) {
//        if (!authenticatedUser.hasAnyPermission(new Permissions[]{Permissions.ADVISER_ADVICE_CATEGORIES})) {
//            return "denied";
//        }

        return "advice-categories";
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
