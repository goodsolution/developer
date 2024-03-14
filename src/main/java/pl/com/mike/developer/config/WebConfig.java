package pl.com.mike.developer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH); // Set default Locale as English
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/img/**",
                        "/css/**",
                        "/libs/**",
                        "/fonts/**",
                        "/js/**",
                        "/angular/**",
                        "/assets/i18n/**",
                        "/assets/img/domdevelopment/**",
                        "/assets/img/antal/**"
                )
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/libs/",
                        "classpath:/static/fonts/",
                        "classpath:/static/js/",
                        "classpath:/static/angular/",
                        "classpath:/static/angular/assets/i18n/",
                        "classpath:/static/angular/assets/img/domdevelopment/",
                        "classpath:/static/angular/assets/img/antal/"
                );
    }

}
