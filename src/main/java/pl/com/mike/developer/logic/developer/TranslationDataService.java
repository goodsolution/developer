package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.TranslationGetResponse;
import pl.com.mike.developer.domain.developer.Translation;
import pl.com.mike.developer.domain.developer.TranslationData;

@Service
public class TranslationDataService {

    private final TranslationDataRepository translationDataRepository;

    public TranslationDataService(TranslationDataRepository translationDataRepository) {
        this.translationDataRepository = translationDataRepository;
    }

    public TranslationGetResponse getTranslation(TranslationData translationRequest) {
        return translationDataRepository.findByEntityIdAndLanguageCodeAndDomainAndKey(
                        translationRequest.getEntityId(),
                        translationRequest.getLanguageCode(),
                        translationRequest.getDomain(),
                        translationRequest.getKey()
                ).map(this::convertToTranslationData)
                .map(translationData -> new TranslationGetResponse(translationData.getValue()))
                .orElseGet(() -> new TranslationGetResponse("Translation not found"));
    }

    private TranslationData convertToTranslationData(Translation translation) {
        return new TranslationData(
                translation.getEntityId(),
                translation.getLanguageCode(),
                translation.getDomain(),
                translation.getKey(),
                translation.getValue()
        );
    }
}
