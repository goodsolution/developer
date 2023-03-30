package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.TokenData;

import java.time.LocalDateTime;

@Service
public class EmailConfirmationService {

    private TokensService tokensService;
    private CourseCustomersService courseCustomersService;
    private EmailService emailService;

    public EmailConfirmationService(TokensService tokensService, CourseCustomersService courseCustomersService, EmailService emailService) {
        this.tokensService = tokensService;
        this.courseCustomersService = courseCustomersService;
        this.emailService = emailService;
    }

    public void confirmEmail(String tokenValue) {

        TokenData token = tokensService.get(tokenValue);

        if(token.getExpirationDatetime().isAfter(LocalDateTime.now())) {
            courseCustomersService.update(new CustomerData(token.getCustomer(), true));
        } else {
            throw new IllegalArgumentException("Token expired");
        }

    }

    public void sendEmailConfirmationLink() {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        emailService.sendEmailConfirmationLink(loggedCustomer);
    }



}
