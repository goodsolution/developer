package pl.com.goodsolution.adviser.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.api.courseplatform.*;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.logic.courseplatform.*;

@Controller
public class AdviserController {
    private static final Logger log = LoggerFactory.getLogger(AdviserController.class);

    private CourseCustomersService courseCustomersService;

    public AdviserController(CourseCustomersService courseCustomersService) {
        this.courseCustomersService = courseCustomersService;
    }

    @GetMapping("/adviser")
    public String adviser(Model model) {
        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer != null) {
            model.addAttribute("loggedCustomer", new CustomerGetResponse(customer));
        }
        return "front-adviser";
    }

    @GetMapping({"/my-advise-categories"})
    public String myAdviseCategories(Model model) {
        return "front-my-advise-categories";
    }

    @GetMapping({"/my-advise-answers"})
    public String myAdviseAnswers(Model model) {
        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer != null) {
            model.addAttribute("loggedCustomer", new CustomerGetResponse(customer));
        }

        return "front-my-advise-answers";
    }

    @GetMapping("/my-advises")
    public String myAdvises(Model model) {
        CustomerData customer = courseCustomersService.getLoggedCustomer();
        if (customer != null) {
            model.addAttribute("loggedCustomer", new CustomerGetResponse(customer));
        }

        return "front-my-advises";
    }
}
