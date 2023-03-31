package pl.com.mike.developer.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import pl.com.mike.developer.api.courseplatform.MemeGetResponse;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.MemeData;
import pl.com.mike.developer.domain.vin.CarsFilter;
import pl.com.mike.developer.logic.*;
import pl.com.mike.developer.logic.courseplatform.AuthorsService;
import pl.com.mike.developer.logic.courseplatform.FilesService;
import pl.com.mike.developer.logic.courseplatform.MemesService;
import pl.com.mike.developer.logic.courseplatform.ModulesService;
import pl.com.mike.developer.logic.vin.CepikService;
import pl.com.mike.developer.logic.vin.VehicleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    public ApplicationController(CustomersService customersService, DictionariesService dictionariesService, CacheService cacheService, FilesService filesService, ApplicationConfig applicationConfig, TemplateEngine templateEngine, MemesService memesService, AuthorsService authorsService, ModulesService modulesService, Environment environment, CepikService cepikService, VehicleService vehicleService) {
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

}
