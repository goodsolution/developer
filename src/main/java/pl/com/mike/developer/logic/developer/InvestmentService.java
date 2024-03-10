package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.developer.CityData;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.List;

@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final ApplicationConfig applicationConfig;

    public InvestmentService(InvestmentRepository investmentRepository, ApplicationConfig applicationConfig) {
        this.investmentRepository = investmentRepository;
        this.applicationConfig = applicationConfig;
    }

    public List<String> getInvestmentCitiesByDeveloperCode() {
        return investmentRepository.getInvestmentCitiesByDeveloperCode(applicationConfig.getSystemCode());
    }

    public List<InvestmentData> getInvestmentsByDeveloperCode() {
        return investmentRepository.getInvestmentsByDeveloperCode(applicationConfig.getSystemCode());
    }

//    public List<InvestmentData> getInvestmentsByDeveloperCodeAndCity(String city) {
//        return investmentRepository.getInvestmentsByDeveloperCodeAndCity(applicationConfig.getSystemCode(), city);
//    }

    public List<InvestmentData> getInvestmentsByPremiseId(Long premiseId) {
        return investmentRepository.getInvestmentsByPremiseId(premiseId);
    }

}
