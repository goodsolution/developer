package pl.com.mike.developer.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DictionaryCepikData {
    private String id;
    private String type;
    @JsonProperty("links")
    private DictionaryCepikDataLink dictionaryCepikDataLink;
    @JsonProperty("attributes")
    private DictionaryCepikDataAttributes dictionaryCepikDataAttributes;

    public DictionaryCepikData(String id, String type, DictionaryCepikDataLink dictionaryCepikDataLink, DictionaryCepikDataAttributes dictionaryCepikDataAttributes) {
        this.id = id;
        this.type = type;
        this.dictionaryCepikDataLink = dictionaryCepikDataLink;
        this.dictionaryCepikDataAttributes = dictionaryCepikDataAttributes;
    }

    public DictionaryCepikData() {
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public DictionaryCepikDataLink getDictionaryCepikDataLink() {
        return dictionaryCepikDataLink;
    }

    public DictionaryCepikDataAttributes getDictionaryCepikDataAttributes() {
        return dictionaryCepikDataAttributes;
    }
}
