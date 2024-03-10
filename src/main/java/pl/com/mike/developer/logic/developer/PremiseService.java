package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.EnhancedPremiseGetResponse;
import pl.com.mike.developer.domain.developer.EnhancedPremiseData;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PremiseService {
    private final PremiseRepository premiseRepository;
    private final CustomPremiseRepositoryImpl customPremiseRepository;


    public PremiseService(PremiseRepository premiseRepository, CustomPremiseRepositoryImpl customPremiseRepository1) {
        this.premiseRepository = premiseRepository;
        this.customPremiseRepository = customPremiseRepository1;
    }

    public EnhancedPremiseGetResponse findPremisePriceRangeByInvestmentId(Long id) {
        EnhancedPremiseData priceRangeByInvestmentId = premiseRepository.findPremisePriceRangeByInvestmentId(id);
        return new EnhancedPremiseGetResponse(priceRangeByInvestmentId.getMinPrice(), priceRangeByInvestmentId.getMaxPrice());
    }

    public List<PremiseData> findPriceByInvestmentId(Long id, String priceFunction) {
        return customPremiseRepository.findPriceByInvestmentId(id, priceFunction);
    }

    public List<PremiseData> getPremiseDataByInvestmentId(PremiseSearchFilter filter) {
        List<PremiseData> premises = new ArrayList<>();
        Iterable<PremiseData> optionalPremiseData = premiseRepository.findAllByInvestmentId(filter.getId());
        if (optionalPremiseData.iterator().hasNext()) {
            optionalPremiseData.forEach(premises::add);
        } else {
            throw new NoSuchElementException();
        }
        return premises;
    }

    public List<PremiseData> getPremiseDataById(PremiseSearchFilter filter) {
        List<PremiseData> premises = new ArrayList<>();
        Optional<PremiseData> optionalPremiseData = premiseRepository.findById(filter.getId());
        if (optionalPremiseData.isPresent()) {
            premises.add(optionalPremiseData.get());
        } else {
            throw new NoSuchElementException();
        }
        return premises;
    }

    public List<PremiseData> getAllPremises() {
        List<PremiseData> premises = new ArrayList<>();
        Iterable<PremiseData> optionalPremiseData = premiseRepository.findAll();
        if (optionalPremiseData.iterator().hasNext()) {
            optionalPremiseData.forEach(premises::add);
        } else {
            throw new NoSuchElementException();
        }
        return premises;
    }

}
