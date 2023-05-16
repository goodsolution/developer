package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.*;

@Service
public class PremiseService {
    private final PremiseRepository premiseRepository;

    public PremiseService(PremiseRepository premiseRepository) {
        this.premiseRepository = premiseRepository;
    }

    public List<PremiseData> getPremiseDataById(PremiseSearchFilter filter) {
        List<PremiseData> premises = new ArrayList<>();
        Optional<PremiseData> list = premiseRepository.findById(filter.getId());
        if(list.isPresent()){
            premises.add(list.get());
        } else{
            throw new NoSuchElementException();
        }
        return premises;
    }

}
