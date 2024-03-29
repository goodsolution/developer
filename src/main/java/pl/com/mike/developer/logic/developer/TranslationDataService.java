package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.TranslationData;
import pl.com.mike.developer.web.TranslationGetResponse;

@Service
public class TranslationDataService {
    private final TranslationDataRepository translationDataRepository;

    public TranslationDataService(TranslationDataRepository translationDataRepository) {
        this.translationDataRepository = translationDataRepository;
    }

    public TranslationGetResponse getTranslation(TranslationData request) {
        return new TranslationGetResponse(translationDataRepository
                .findByEntityIdAndLanguageCodeAndDomainAndKey(
                        request.getEntityId(),
                        request.getLocale().getLanguage(),
                        request.getDomain(),
                        request.getKey()
                )
                .map(pl.com.mike.developer.domain.developer.TranslationData::getValue)
                .orElseThrow(() -> new RuntimeException("Translation not found"))
        );
    }

}