package pl.com.mike.developer.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class DictionaryMeta {
    private int count;
    private int page;
    private int limit;
    @JsonProperty("json-ld-id")
    private String jsonLdId;
    private String schema;
    @JsonProperty("dc:title")
    private String dcTitle;
    @JsonProperty("schema:provider")
    private String schemaProvider;
    @JsonProperty("schema:datePublished")
    private LocalDate schemaDatePublished;
    @JsonProperty("schema:dateModified")
    private LocalDate schemaDateModified;
    @JsonProperty("dc:description")
    private String dcDescription;
    @JsonProperty("dc:language")
    private String dcLanguage;
    @JsonProperty("sy:updatePeriod")
    private String syUpdatePeriod;
    @JsonProperty("sy:updateFrequency")
    private String syUpdateFrequency;
    @JsonProperty("sy:updateBase")
    private LocalDate syUpdateBase;
    @JsonProperty("schema:contentType")
    private String schemaContentType;
    @JsonProperty("schema:isPartOf")
    private String schemaIsPartOf;
    @JsonProperty("dc:rights")
    private String dcRights;
    @JsonProperty("schema:keywords")
    private String schemaKeywords;

    public DictionaryMeta(int count, int page, int limit, String jsonLdId, String schema, String dcTitle, String schemaProvider, LocalDate schemaDatePublished, LocalDate schemaDateModified, String dcDescription, String dcLanguage, String syUpdatePeriod, String syUpdateFrequency, LocalDate syUpdateBase, String schemaContentType, String schemaIsPartOf, String dcRights, String schemaKeywords) {
        this.count = count;
        this.page = page;
        this.limit = limit;
        this.jsonLdId = jsonLdId;
        this.schema = schema;
        this.dcTitle = dcTitle;
        this.schemaProvider = schemaProvider;
        this.schemaDatePublished = schemaDatePublished;
        this.schemaDateModified = schemaDateModified;
        this.dcDescription = dcDescription;
        this.dcLanguage = dcLanguage;
        this.syUpdatePeriod = syUpdatePeriod;
        this.syUpdateFrequency = syUpdateFrequency;
        this.syUpdateBase = syUpdateBase;
        this.schemaContentType = schemaContentType;
        this.schemaIsPartOf = schemaIsPartOf;
        this.dcRights = dcRights;
        this.schemaKeywords = schemaKeywords;
    }

    public DictionaryMeta() {
    }

    public int getCount() {
        return count;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public String getJsonLdId() {
        return jsonLdId;
    }

    public String getSchema() {
        return schema;
    }

    public String getDcTitle() {
        return dcTitle;
    }

    public String getSchemaProvider() {
        return schemaProvider;
    }

    public LocalDate getSchemaDatePublished() {
        return schemaDatePublished;
    }

    public LocalDate getSchemaDateModified() {
        return schemaDateModified;
    }

    public String getDcDescription() {
        return dcDescription;
    }

    public String getDcLanguage() {
        return dcLanguage;
    }

    public String getSyUpdatePeriod() {
        return syUpdatePeriod;
    }

    public String getSyUpdateFrequency() {
        return syUpdateFrequency;
    }

    public LocalDate getSyUpdateBase() {
        return syUpdateBase;
    }

    public String getSchemaContentType() {
        return schemaContentType;
    }

    public String getSchemaIsPartOf() {
        return schemaIsPartOf;
    }

    public String getDcRights() {
        return dcRights;
    }

    public String getSchemaKeywords() {
        return schemaKeywords;
    }
}
