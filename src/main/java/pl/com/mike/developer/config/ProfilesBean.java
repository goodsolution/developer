package pl.com.mike.developer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProfilesBean {
    private final String[] activeProfiles;
    private final Logger logger = LoggerFactory.getLogger(ProfilesBean.class);

    public ProfilesBean(@Value("${spring.profiles.active:}") String activeProfiles) {
        this.activeProfiles = activeProfiles.split(",");
    }

    public String getActiveProfiles() {
        if (activeProfiles.length > 0) {
            return "application-" + activeProfiles[0] + ".properties";
        } else {
            logger.error("No active profile found");
            return "application.properties";
        }
    }

}