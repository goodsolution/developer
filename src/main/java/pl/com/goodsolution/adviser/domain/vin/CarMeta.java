package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarMeta {

    @JsonProperty("json-ld-id")
    private String jsonLdId;
    private String schema;
    @JsonProperty("dc:title")
    private String title;
    @JsonProperty("schema:provider")
    private String schemaProvider;
    @JsonProperty("schema:datePublished")
    private String schemaDatePublished;
    @JsonProperty("schema:dateModified")
    private String schemaDateModified;
    @JsonProperty("dc:description")
    private String dcDescription;
    @JsonProperty("dc:language")
    private String dcLanguage;
    @JsonProperty("sy:updatePeriod")
    private String syUpdatePeriod;
    @JsonProperty("sy:updateFrequency")
    private String syUpdateFrequency;
    @JsonProperty("sy:updateBase")
    private String syUpdateBase;
    @JsonProperty("schema:contentType")
    private String schemaContentType;
    @JsonProperty("schema:isPartOf")
    private String schemaIsPartOf;
    private Integer count;
    private Integer page;
    private Integer limit;
    @JsonProperty("dc:rights")
    private String dcRights;
    @JsonProperty("schema:keywords")
    private String schemaKeywords;

    public CarMeta(String jsonLdId, String schema, String title, String schemaProvider, String schemaDatePublished, String schemaDateModified, String dcDescription, String dcLanguage, String syUpdatePeriod, String syUpdateFrequency, String syUpdateBase, String schemaContentType, String schemaIsPartOf, Integer count, Integer page, Integer limit, String dcRights, String schemaKeywords) {
        this.jsonLdId = jsonLdId;
        this.schema = schema;
        this.title = title;
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
        this.count = count;
        this.page = page;
        this.limit = limit;
        this.dcRights = dcRights;
        this.schemaKeywords = schemaKeywords;
    }

    public CarMeta() {
    }

    public String getJsonLdId() {
        return jsonLdId;
    }

    public String getSchema() {
        return schema;
    }

    public String getTitle() {
        return title;
    }

    public String getSchemaProvider() {
        return schemaProvider;
    }

    public String getSchemaDatePublished() {
        return schemaDatePublished;
    }

    public String getSchemaDateModified() {
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

    public String getSyUpdateBase() {
        return syUpdateBase;
    }

    public String getSchemaContentType() {
        return schemaContentType;
    }

    public String getSchemaIsPartOf() {
        return schemaIsPartOf;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getDcRights() {
        return dcRights;
    }

    public String getSchemaKeywords() {
        return schemaKeywords;
    }
}
