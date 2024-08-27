package pl.com.mike.developer.elearning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.mike.developer.elearning.service.CommonValidator;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public CommonValidator commonValidator() {
        return new CommonValidator();
    }

}
