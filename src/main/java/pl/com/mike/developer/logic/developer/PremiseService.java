package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PremiseService {
    private final PremiseRepository premiseRepository;

    public PremiseService(PremiseRepository premiseRepository) {
        this.premiseRepository = premiseRepository;
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
