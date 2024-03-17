package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "translations")
public class TranslationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "domain")
    private String domain;

    @Column(name = "`key`") // Use backticks in the column definition to avoid SQL reserved keyword conflicts
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at", nullable = true) // Marking this as nullable since deleted_at can be null
    private Timestamp deletedAt;

    public TranslationData() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
