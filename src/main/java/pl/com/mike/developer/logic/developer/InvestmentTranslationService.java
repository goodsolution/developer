package pl.com.mike.developer.logic.developer;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.InvestmentTranslationData;

import java.util.Locale;

@Service
public class InvestmentTranslationService {

    private InvestmentTranslationRepository investmentTranslationRepository;

    public InvestmentTranslationService(InvestmentTranslationRepository investmentTranslationRepository) {
        this.investmentTranslationRepository = investmentTranslationRepository;
    }

    public String getTranslation(Integer investmentId, Locale locale, String domain) {
        return investmentTranslationRepository
                .findByInvestmentIdAndLanguageCodeAndDomain(investmentId, locale.getLanguage(), domain)
                .map(InvestmentTranslationData::getValue)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
    }
}
