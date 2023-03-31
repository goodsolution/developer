package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.PasswordResetTokenData;
import pl.com.mike.developer.domain.courseplatform.PasswordResetTokensFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordResetTokensService {

    private PasswordResetTokensJdbcRepository passwordResetTokensJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private ApplicationConfig applicationConfig;

    public PasswordResetTokensService(PasswordResetTokensJdbcRepository passwordResetTokensJdbcRepository, CourseCustomersService courseCustomersService, ApplicationConfig applicationConfig) {
        this.passwordResetTokensJdbcRepository = passwordResetTokensJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.applicationConfig = applicationConfig;
    }

    public void create(PasswordResetTokenData data) {
        passwordResetTokensJdbcRepository.create(data);
    }

    public List<PasswordResetTokenData> find(PasswordResetTokensFilter filter) {
        return passwordResetTokensJdbcRepository.find(filter);
    }

    public void update(PasswordResetTokenData data){
        passwordResetTokensJdbcRepository.update(data);
    }


    private PasswordResetTokenData prepare(CustomerData customer) {
        return new PasswordResetTokenData(customer, UUID.randomUUID().toString(),
                LocalDateTime.now().plusMinutes(applicationConfig.getPasswordResetTokenValidityTimeInMinutes()), false);
    }

    private void validateTokenBeforeReset(PasswordResetTokenData data) {
        if(LocalDateTime.now().isAfter(data.getExpirationDatetime())) {
            throw new IllegalArgumentException("Token expired");
        } else if (data.getUsed()) {
            throw new IllegalArgumentException("Token used, can't reset password");
        }
    }
}
