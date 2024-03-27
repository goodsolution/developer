package pl.com.mike.developer.logic.developer;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.TranslationData;

import java.util.Optional;

@Repository
public interface TranslationDataRepository extends JpaRepository<TranslationData, Long> {

    @Cacheable(CacheType.TRANSLATIONS)
    Optional<TranslationData> findByEntityIdAndLanguageCodeAndDomainAndKey(
            Integer entityId, String languageCode, String domain, String key
    );

}