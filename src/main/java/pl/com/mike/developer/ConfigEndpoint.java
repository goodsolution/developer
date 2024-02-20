package pl.com.mike.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.logic.developer.DeveloperSearchFilter;
import pl.com.mike.developer.logic.developer.DeveloperService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("api/system/")
public class ConfigEndpoint {
    private final DeveloperService developerService;
    private final ApplicationConfig applicationConfig;
    private final Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(ConfigEndpoint.class);

    public ConfigEndpoint(DeveloperService developerService, ApplicationConfig applicationConfig, Environment environment) {
        this.developerService = developerService;
        this.applicationConfig = applicationConfig;
        this.environment = environment;
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
        String[] activeProfiles = environment.getActiveProfiles();
        String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : "default";
        Properties prop = new Properties();
        String propertiesFileName = "application-" + activeProfile + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                logger.error("Properties file not found: {}", propertiesFileName);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Map<String, String> map = new HashMap<>();
        for (String key : prop.stringPropertyNames()) {
            map.put(key, prop.getProperty(key));
        }
        return ResponseEntity.ok(map);
    }

    private String getUrl(String logoFileName) {
        return applicationConfig.getSystemAsset() + applicationConfig.getSystemCode() + logoFileName;
    }

}