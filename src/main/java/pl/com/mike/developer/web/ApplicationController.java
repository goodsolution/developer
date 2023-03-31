package pl.com.mike.developer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import pl.com.mike.developer.api.courseplatform.*;
import pl.com.mike.developer.auth.Permissions;
import pl.com.mike.developer.config.ApplicationConfig;
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
import java.time.LocalDate;

@Controller
public class ApplicationController {
    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    private CustomersService customersService;
    private DictionariesService dictionariesService;
    private CacheService cacheService;

    private FilesService filesService;

    private ApplicationConfig applicationConfig;

    private TemplateEngine templateEngine;

    private MemesService memesService;
    private AuthorsService authorsService;

    private ModulesService modulesService;
    private Environment environment;

    private CepikService cepikService;
    private VehicleService vehicleService;

    public ApplicationController( CustomersService customersService, DictionariesService dictionariesService, CacheService cacheService, FilesService filesService, ApplicationConfig applicationConfig,TemplateEngine templateEngine, MemesService memesService, AuthorsService authorsService, ModulesService modulesService, Environment environment, CepikService cepikService, VehicleService vehicleService) {
        this.customersService = customersService;
        this.dictionariesService = dictionariesService;
        this.cacheService = cacheService;

        this.filesService = filesService;
        this.applicationConfig = applicationConfig;
        this.templateEngine = templateEngine;
        this.memesService = memesService;
        this.authorsService = authorsService;
        this.modulesService = modulesService;

        this.environment = environment;
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








    @GetMapping({"/test-sets"})
    public String testSets(Model model) {
        return "test-sets";
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
