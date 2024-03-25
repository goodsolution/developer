package pl.com.mike.developer.logic.developer;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.TranslationRequest;
import pl.com.mike.developer.domain.developer.TranslationData;
import pl.com.mike.developer.web.TranslationGetResponse;

@Service
public class TranslationDataService {
    private final TranslationDataRepository translationDataRepository;

    public TranslationDataService(TranslationDataRepository translationDataRepository) {
        this.translationDataRepository = translationDataRepository;
    }

    public TranslationGetResponse getTranslation(TranslationRequest request) {
        return new TranslationGetResponse(translationDataRepository
                .findByEntityIdAndLanguageCodeAndDomainAndKey(
                        request.getEntityId(),
                        request.getLocale().getLanguage(),
                        request.getDomain(),
                        request.getKey()
                )
                .map(TranslationData::getValue)
                .orElseThrow(() -> new RuntimeException("Translation not found"))
        );
    }

}