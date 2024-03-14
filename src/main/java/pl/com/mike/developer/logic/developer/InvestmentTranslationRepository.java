package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.InvestmentTranslationData;

import java.util.Optional;

@Repository
public interface InvestmentTranslationRepository extends JpaRepository<InvestmentTranslationData, Long> {
    Optional<InvestmentTranslationData> findByInvestmentIdAndLanguageCodeAndDomain(
            Integer investmentId,
            String languageCode,
            String domain
    );

}
