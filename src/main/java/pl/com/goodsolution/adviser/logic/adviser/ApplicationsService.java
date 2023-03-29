package pl.com.goodsolution.adviser.logic.adviser;

import org.apache.xmlbeans.impl.common.InvalidLexicalValueException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.ApplicationData;
import pl.com.goodsolution.adviser.domain.adviser.ApplicationsFilter;
import pl.com.goodsolution.adviser.logic.CacheType;

import java.util.List;

@Service
public class ApplicationsService {

    private final ApplicationsJdbcRepository applicationsJdbcRepository;

    public ApplicationsService(ApplicationsJdbcRepository applicationsJdbcRepository) {
        this.applicationsJdbcRepository = applicationsJdbcRepository;
    }

    public void create(ApplicationData data) {
        validate(data);
        applicationsJdbcRepository.create(data);
    }

    public ApplicationData get(Long id) {
        return applicationsJdbcRepository.get(id);
    }

    @Cacheable(CacheType.APPLICATIONS)
    public List<ApplicationData> find(ApplicationsFilter filter) {
        return applicationsJdbcRepository.find(filter);
    }

    public void update(ApplicationData data) {
        validate(data);
        applicationsJdbcRepository.update(data);
    }

    public void delete(Long id) {
        applicationsJdbcRepository.delete(id);
    }

    public Long getTotalApplicationsCount() {
        return applicationsJdbcRepository.getTotalApplicationsCount();
    }

    private void validate(ApplicationData data) {
        if(data.getApplicationId().equals("")) {
            throw new IllegalArgumentException("Application ID can't be empty");
        } else if(data.getApplicationId().length() > 200) {
            throw new IllegalArgumentException("Application ID too long");
        } else if(data.getDescription().length() > 5000) {
            throw new IllegalArgumentException("Description too long");
        } else if(data.getSecretKey().length() < 30) {
            throw new IllegalArgumentException("Secret key is too short");
        } else if(data.getSecretKey().length() > 1000) {
            throw new InvalidLexicalValueException("Secret key is too long");
        } else if(applicationsJdbcRepository.checkIfSecretKeyTaken(data.getSecretKey())) {
            throw new IllegalArgumentException("Secret key taken");
        }
    }

    public void validateApplication(String appId, String secret) {
        List<ApplicationData> list = find(new ApplicationsFilter(null, null, null, appId, secret));
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Application: " + appId + " no defined (or invalid secret key)");
        }
    }
}
