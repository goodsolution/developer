package pl.com.mike.developer.auth;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.logic.developer.InvestmentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final InvestmentService investmentService;

    public MenuService(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    public List<Menu> getMenu(String systemCode) {
        Map<String, List<Menu>> menuByCode = new HashMap<>();
        menuByCode.put("antal",
                new ArrayList<>(Arrays.asList(
                        new Menu("Cities", "/", getCities()),
                        new Menu("Contact", "/developer/contact", Collections.emptyList())
                )));
        menuByCode.put("domdevelopment",
                new ArrayList<>(Arrays.asList(
                        new Menu("Cities", "/", getCities()),
                        new Menu("Contact", "/developer/contact", Collections.emptyList())
                )));
        return menuByCode.get(systemCode);
    }

    private List<Menu> getCities() {
        return investmentService.getInvestmentCitiesByDeveloperCode().stream()
                .map(x -> new Menu(x, "", Collections.emptyList()))
                .collect(Collectors.toList());
    }

}
