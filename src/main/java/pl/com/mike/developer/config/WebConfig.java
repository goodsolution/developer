package pl.com.mike.developer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final ApplicationConfig applicationConfig;

    public WebConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/img/**",
                        "/css/**",
                        "/libs/**",
                        "/fonts/**",
                        "/js/**",
                        "/angular/**"
                )
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/libs/",
                        "classpath:/static/fonts/",
                        "classpath:/static/js/",
                        "classpath:/static/angular/"
                );

        //TODO analyze and delete
        registry.addResourceHandler("/gallery/**")
                .addResourceLocations(new File(applicationConfig.getPathToGallery()).toURI().toString());

        registry.addResourceHandler("/course-platform-images/**")
                .addResourceLocations(new File(applicationConfig.getCoursePathToGallery()).toURI().toString());

        registry.addResourceHandler("/course-platform-invoices/**")
                .addResourceLocations(new File(applicationConfig.getCoursePathToInvoicesFolder()).toURI().toString());
    }

}
