package pl.com.goodsolution.adviser.config.courseplatform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class CoursePlatformConfig {

    @Value("${course-platform-path-to-gallery}")
    private String pathToGallery;

    public String getPathToGallery() {
        return pathToGallery;
    }
}
