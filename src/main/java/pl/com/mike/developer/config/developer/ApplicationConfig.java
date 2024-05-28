package pl.com.mike.developer.config.developer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class ApplicationConfig {

    @Value("${system.code}")
    private String systemCode;

    @Value("${system.prefix}")
    private String systemPrefix;

    public String getSystemPrefix() {
        return systemPrefix;
    }

    public String getSystemCode() {
        return systemCode;
    }

}
