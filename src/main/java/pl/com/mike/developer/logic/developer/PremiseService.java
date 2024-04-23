package pl.com.mike.developer.logic.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.DictionaryGetResponse;
import pl.com.mike.developer.PremiseAggregatedValuesGetResponse;
import pl.com.mike.developer.domain.developer.DictionaryData;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PremiseService {

    private static final String TECHNICAL_STATUS = "premises.technicalStatus";
    private static final String SALES_STATUS = "premises.salesStatus";
    private static final String EXPOSURE = "premises.exposure";

    private final PremiseRepository premiseRepository;
    private final TranslationDataService translationDataService;

    private final Logger logger = LoggerFactory.getLogger(PremiseService.class);

    public PremiseService(PremiseRepository premiseRepository, TranslationDataService translationDataService) {
        this.premiseRepository = premiseRepository;
        this.translationDataService = translationDataService;
    }

    public PremiseAggregatedValuesGetResponse findPremisePriceRangeByInvestmentId(Long id) {
        return new PremiseAggregatedValuesGetResponse(premiseRepository.findPremisePriceRangeByInvestmentId(id));
    }

    public List<PremiseData> getPremiseDataById(PremiseSearchFilter filter) {
        return premiseRepository.findById(filter.getId()).stream()
                .map(PremiseData::new)
                .map(premiseData -> setTranslationsAndLanguageCodeToPremiseData(premiseData, filter.getLanguageCode()))
                .toList();
    }

    public List<PremiseData> getPremiseDataByInvestmentId(PremiseSearchFilter filter) {
        return premiseRepository.findAllByInvestmentId(filter.getId()).stream()
                .map(PremiseData::new)
                .map(premiseData -> setTranslationsAndLanguageCodeToPremiseData(premiseData, filter.getLanguageCode()))
                .toList();
    }

    public PremiseData setTranslationsAndLanguageCodeToPremiseData(PremiseData premiseData, String languageCode) {
        premiseData.setTechnicalStatusTranslation(fetchTranslation(languageCode, TECHNICAL_STATUS, premiseData.getTechnicalStatus()));
        premiseData.setSalesStatusTranslation(fetchTranslation(languageCode, SALES_STATUS, premiseData.getSalesStatus()));
        premiseData.setExposureTranslation(fetchTranslation(languageCode, EXPOSURE, premiseData.getExposure()));
        premiseData.setLanguageCode(languageCode);
        return premiseData;
    }

    public String fetchTranslation(String languageCode, String domain, String key) {
        try {
            DictionaryGetResponse response = translationDataService.getDictionary(new DictionaryData(languageCode, domain, key));
            return Optional.ofNullable(response.getTranslation()).orElse("Default Translation");
        } catch (NoSuchElementException e) {
            logger.error("Translation not found for domain: {} and key: {}", domain, key, e);
            return "Default Translation";
        }
    }

}
