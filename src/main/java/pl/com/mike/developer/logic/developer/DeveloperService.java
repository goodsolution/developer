package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }




}
