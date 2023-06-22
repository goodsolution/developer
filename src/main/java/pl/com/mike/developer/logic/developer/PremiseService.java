package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PremiseService {
    private final PremiseRepository premiseRepository;

    public PremiseService(PremiseRepository premiseRepository) {
        this.premiseRepository = premiseRepository;
    }

//    public List<PremiseData> getPremiseDataById(PremiseSearchFilter filter) {
//        List<PremiseData> premises = new ArrayList<>();
//        Optional<PremiseData> list = premiseRepository.findById(filter.getId());
//        if(list.isPresent()){
//            premises.add(list.get());
//        } else{
//            throw new NoSuchElementException();
//        }
//        return premises;
//    }

    public List<PremiseData> getAllPremisesData() {
        return premiseRepository.findAll();
    }

    public PremiseData getPremiseDataById(PremiseSearchFilter filter) {
        Optional<PremiseData> premise = premiseRepository.findById(filter.getId());
        if(premise.isPresent()){
            return premise.get();
        } else{
            throw new NoSuchElementException();
        }
    }


    public void update(PremiseData premiseData) {
        premiseRepository.save(premiseData);
    }

}
