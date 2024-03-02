package pl.com.mike.developer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActiveProfileConfigLoader {
    private final String activeProfile;
    private final Logger logger = LoggerFactory.getLogger(ActiveProfileConfigLoader.class);

    public ActiveProfileConfigLoader(@Value("${spring.profiles.active:}") String activeProfiles) {
        this.activeProfile = activeProfiles;
    }

    @PostConstruct
    private void checkActiveProfile() {
        if (activeProfile.isEmpty()) {
            logger.warn("No active profile set, running with default configuration");
        } else {
            logger.info("Running with active profile: {}", activeProfile);
        }
    }

    public String getActiveProfilePropertiesPath() {
        return "application-" + activeProfile + ".properties";
    }

}