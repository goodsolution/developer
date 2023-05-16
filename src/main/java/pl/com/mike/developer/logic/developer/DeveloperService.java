package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.DeveloperData;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<DeveloperData> getDeveloperDataById(DeveloperSearchFilter developerSearchFilter) {
        List<DeveloperData> developers = new ArrayList<>();
        developerRepository.findById(developerSearchFilter.getId()).ifPresent(developers::add);
        return developers;
    }


}
