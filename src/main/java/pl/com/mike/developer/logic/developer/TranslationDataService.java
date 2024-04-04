package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.TranslationData;
import pl.com.mike.developer.TranslationGetResponse;
import pl.com.mike.developer.domain.developer.Translation;

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
                        request.getLanguageCode(),
                        request.getDomain(),
                        request.getKey()
                )
                .map(Translation::getValue)
                .orElseThrow(() -> new RuntimeException("Translation not found"))
        );
    }

}