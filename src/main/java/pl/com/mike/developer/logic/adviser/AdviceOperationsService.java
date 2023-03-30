package pl.com.mike.developer.logic.adviser;

import org.springframework.stereotype.Service;

@Service
public class AdviceOperationsService {

    private final ApplicationsJdbcRepository applicationsJdbcRepository;

    public AdviceOperationsService(ApplicationsJdbcRepository applicationsJdbcRepository) {
        this.applicationsJdbcRepository = applicationsJdbcRepository;
    }

    public void run() {
        System.out.println("Brum brum");
    }


}
