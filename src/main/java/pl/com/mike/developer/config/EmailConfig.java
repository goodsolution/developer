package pl.com.mike.developer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${masterdieta-emails-disabled}")
    private Boolean emailsDisabled;

    public Boolean getEmailsDisabled() {
        return emailsDisabled;
    }
}
