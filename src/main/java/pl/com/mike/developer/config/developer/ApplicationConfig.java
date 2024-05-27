package pl.com.mike.developer.config.developer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class ApplicationConfig {

    @Value("${system.code}")
    private String systemCode;

    @Value("angular/assets/img/")
    private String systemAsset;

    @Value("${system.logoDeveloperEndpoint}")
    private String logoDeveloperEndpoint;

    @Value("system.")
    private String systemPrefix;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    @Value("${aes}")
    private String aes;

    @Value("${aes.gcm.no.padding}")
    private String aesNoPadding;

    @Value("${aes.key}")
    private String aesKey;

    @Value("${aes.gcm.iv.length}")
    private int gcmIvLength;

    @Value("${aes.gcm.tag.length}")
    private int gcmTagLength;

    public String getAes() {
        return aes;
    }

    public String getAesNoPadding() {
        return aesNoPadding;
    }

    public String getAesKey() {
        return aesKey;
    }

    public int getGcmIvLength() {
        return gcmIvLength;
    }

    public int getGcmTagLength() {
        return gcmTagLength;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    public String getSystemPrefix() {
        return systemPrefix;
    }

    public String getLogoDeveloperEndpoint() {
        return logoDeveloperEndpoint;
    }

    public String getSystemAsset() {
        return systemAsset;
    }

    public String getSystemCode() {
        return systemCode;
    }

}
