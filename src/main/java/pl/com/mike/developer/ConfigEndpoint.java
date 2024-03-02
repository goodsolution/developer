package pl.com.mike.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.config.ActiveProfileConfigLoader;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;
import pl.com.mike.developer.logic.developer.PropertyConfigService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("api/system/")
public class ConfigEndpoint {
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;
    private final ActiveProfileConfigLoader profileConfigLoader;
    private final PropertyConfigService propertyConfigService;
    private static final Logger logger = LoggerFactory.getLogger(ConfigEndpoint.class);

    public ConfigEndpoint(DeveloperService developerService, ApplicationConfig applicationConfig, ActiveProfileConfigLoader profilesBean, PropertyConfigService propertyService) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
        this.profileConfigLoader = profilesBean;
        this.propertyConfigService = propertyService;
    }

    @GetMapping("developers/logo")
    public LogoUrlGetResponse getLogoUrl() {
        return new LogoUrlGetResponse(
                getUrl(developerService.getDeveloperByCode(new DeveloperSearchFilter(applicationConfig.getSystemCode())).getLogoUrl())
        );
    }

    @GetMapping("code")
    public CodeGetResponse getCodeResponse() {
        return new CodeGetResponse(
                applicationConfig.getSystemCode()
        );
    }

    @GetMapping("config/properties")
    public ResponseEntity<Map<String, String>> getConfiguration() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(profileConfigLoader.getActiveProfilePropertiesPath())) {
            if (input == null) {
                logger.error("Properties file not found: {}", profileConfigLoader.getActiveProfilePropertiesPath());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(propertyConfigService.getFilteredConfigProperties(prop));
    }

    private String getUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }

}