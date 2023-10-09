package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.DeveloperData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<DeveloperData> getDeveloperById(DeveloperSearchFilter filter) {
        List<DeveloperData> developers = new ArrayList<>();
        developers.add(developerRepository.findById(filter.getId()).orElseThrow(NoSuchElementException::new));
        return developers;
    }

    public DeveloperData getDeveloperByCode(DeveloperSearchFilter filter) {
        return developerRepository.getDeveloperDataByCode(filter.getCode()).orElseThrow(NoSuchElementException::new);
    }


}
