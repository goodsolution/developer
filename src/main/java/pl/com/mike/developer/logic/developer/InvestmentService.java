package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.*;

@Service
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    public List<InvestmentData> getInvestmentDataByDeveloperId(InvestmentSearchFilter investmentSearchFilter) {
        List<InvestmentData> investments = new ArrayList<>();
        List<Optional<InvestmentData>> investmentDataByDeveloperId = investmentRepository.findInvestmentDataByDeveloperId(investmentSearchFilter.getDeveloperId());
        for (Optional<InvestmentData> investmentData : investmentDataByDeveloperId) {
            investments.add(investmentData.orElseThrow(NoSuchElementException::new));
        }
        return investments;
    }


}
