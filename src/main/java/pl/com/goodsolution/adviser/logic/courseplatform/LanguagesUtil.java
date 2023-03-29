package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.context.i18n.LocaleContextHolder;
import pl.com.goodsolution.adviser.logic.Language;

import java.util.Locale;

public class LanguagesUtil {

    public static Language getCurrentLanguage() {
        return prepareLanguageFrom(LocaleContextHolder.getLocale());
    }

    public static Language prepareLanguageFrom(Locale locale) {
        return Language.valueOf(locale.getLanguage().toUpperCase());
    }

}
