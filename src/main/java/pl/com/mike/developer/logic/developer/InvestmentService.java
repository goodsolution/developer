package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.CityData;

import java.util.List;

@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    public List<CityData> getInvestmentCitiesByDeveloperId(InvestmentSearchFilter filter) {
        return investmentRepository.getInvestmentCitiesByDeveloperId(filter.getDeveloperId());
    }

}
