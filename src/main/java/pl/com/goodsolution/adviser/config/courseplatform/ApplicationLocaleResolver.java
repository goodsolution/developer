package pl.com.goodsolution.adviser.config.courseplatform;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseCustomersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class ApplicationLocaleResolver extends SessionLocaleResolver {

    @Autowired
    private CourseCustomersService courseCustomersService;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        setLangPreferredByCustomer(request, courseCustomersService.getLoggedCustomer());
        return LocaleContextHolder.getLocale();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        super.setLocale(request, response, locale);
    }

    private void setLangPreferredByCustomer(HttpServletRequest request, CustomerData customer) {
        if(customer != null) {
            setLocale(request, null, Locale.forLanguageTag(customer.getLanguage()));
        }
    }
}
