package pl.com.mike.developer;

import java.util.Locale;
import java.util.Objects;

public class TranslationRequest {
    private Integer entityId;
    private Locale locale;
    private String domain;
    private String key;

    public TranslationRequest(Integer entityId, Locale locale, String domain, String key) {
        this.entityId = entityId;
        this.locale = locale;
        this.domain = domain;
        this.key = key;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationRequest)) return false;
        TranslationRequest that = (TranslationRequest) o;
        return Objects.equals(getEntityId(), that.getEntityId()) && Objects.equals(getLocale(), that.getLocale()) && Objects.equals(getDomain(), that.getDomain()) && Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntityId(), getLocale(), getDomain(), getKey());
    }

}