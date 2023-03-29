package pl.com.goodsolution.adviser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import pl.com.goodsolution.adviser.config.courseplatform.ApplicationLocaleResolver;

import java.util.Locale;


@Configuration
public class LanguagesConfig implements WebMvcConfigurer {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        ApplicationLocaleResolver applicationLocaleResolver = new ApplicationLocaleResolver();
        applicationLocaleResolver.setDefaultLocale(Locale.forLanguageTag("pl-PL"));
        return applicationLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
