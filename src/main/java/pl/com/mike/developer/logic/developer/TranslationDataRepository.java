package pl.com.mike.developer.logic.developer;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.Translation;

import java.util.Optional;

@Repository
public interface TranslationDataRepository extends JpaRepository<Translation, Long> {

    @Cacheable(CacheType.TRANSLATIONS)
    Optional<Translation> findByEntityIdAndLanguageCodeAndDomainAndKey(
            Integer entityId, String languageCode, String domain, String key
    );

    @Cacheable(CacheType.DICTIONARIES)
    Optional<Translation> findByLanguageCodeAndDomainAndKey(String languageCode, String domain, String key);
}