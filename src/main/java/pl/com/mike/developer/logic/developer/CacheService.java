package pl.com.mike.developer.logic.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    @CacheEvict(
            value = CacheType.TRANSLATIONS,
            allEntries = true
    )
    public void invalidateTranslations() {
        logger.info("Invalidating translations cache");
    }

}