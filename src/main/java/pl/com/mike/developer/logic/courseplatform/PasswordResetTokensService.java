package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;
import pl.com.mike.developer.domain.courseplatform.PasswordResetTokenData;
import pl.com.mike.developer.domain.courseplatform.PasswordResetTokensFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordResetTokensService {

    private PasswordResetTokensJdbcRepository passwordResetTokensJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private EmailService emailService;
    private ApplicationConfig applicationConfig;

    public PasswordResetTokensService(PasswordResetTokensJdbcRepository passwordResetTokensJdbcRepository, CourseCustomersService courseCustomersService, EmailService emailService, ApplicationConfig applicationConfig) {
        this.passwordResetTokensJdbcRepository = passwordResetTokensJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.emailService = emailService;
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

    public void prepareAndSendToken(String email) {
        try {
            CustomerData customer = courseCustomersService.find(new CustomersFilter(email)).get(0);
            PasswordResetTokenData token = prepare(customer);
            create(token);
            emailService.sendPasswordResetMail(token);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("We can't find account with that email");
        }
    }

    public void resetPassword(String token, String newPasswordHash) {
        try{
            PasswordResetTokenData tokenData = find(new PasswordResetTokensFilter(token)).get(0);
            validateTokenBeforeReset(tokenData);
            courseCustomersService.changePassword(tokenData.getCustomer(), newPasswordHash);
            update(new PasswordResetTokenData(tokenData, true));
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Incorrect token!");
        }
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
