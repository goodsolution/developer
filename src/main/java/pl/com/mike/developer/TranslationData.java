package pl.com.mike.developer;

public class TranslationData {
    private Long translationId;
    private Integer entityId;
    private String languageCode;
    private String domain;
    private String key;

    public TranslationData(Integer entityId, String languageCode, String domain, String key) {
        this.entityId = entityId;
        this.languageCode = languageCode;
        this.domain = domain;
        this.key = key;
    }

    public Long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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
}