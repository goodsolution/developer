package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.DictionaryGetResponse;
import pl.com.mike.developer.TranslationGetResponse;

import pl.com.mike.developer.domain.developer.DictionaryData;
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
                ).map(this::convertToData)
                .map(translationData -> new TranslationGetResponse(translationData.getValue()))
                .orElseGet(() -> new TranslationGetResponse("Translation not found"));
    }

    public DictionaryGetResponse getDictionary(DictionaryData dictionaryData) {
        return translationDataRepository.findByLanguageCodeAndDomainAndKey(
                        dictionaryData.getLanguageCode(),
                        dictionaryData.getDomain(),
                        dictionaryData.getKey()
                ).map(this::convertToData)
                .map(translationData -> new DictionaryGetResponse(translationData.getValue()))
                .orElseGet(() -> new DictionaryGetResponse("Translation not found"));
    }
    private TranslationData convertToData(Translation translation) {
        return new TranslationData(
                translation.getEntityId(),
                translation.getLanguageCode(),
                translation.getDomain(),
                translation.getKey(),
                translation.getValue()
        );
    }


}
