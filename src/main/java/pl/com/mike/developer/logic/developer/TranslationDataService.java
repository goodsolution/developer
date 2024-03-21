package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.TranslationData;
import pl.com.mike.developer.web.TranslationGetResponse;

import java.util.Locale;

@Service
public class TranslationDataService {
    private final TranslationDataRepository translationDataRepository;

    public TranslationDataService(TranslationDataRepository translationDataRepository) {
        this.translationDataRepository = translationDataRepository;
    }

    public TranslationGetResponse getTranslation(Integer entityId, Locale locale, String domain, String key) {
        return new TranslationGetResponse(translationDataRepository
                .findByEntityIdAndLanguageCodeAndDomainAndKey(entityId, locale.getLanguage(), domain, key)
                .map(TranslationData::getValue)
                .orElseThrow(() -> new RuntimeException("Translation not found"))
        );
    }

}