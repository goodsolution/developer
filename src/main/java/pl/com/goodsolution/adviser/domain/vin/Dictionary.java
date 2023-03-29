package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dictionary {
    @JsonProperty("meta")
    private DictionaryMeta dictionaryMeta;
    @JsonProperty("links")
    private DictionaryLinks dictionaryLinks;
    @JsonProperty("data")
    private DictionaryCepikData dictionaryCepikData;

    public Dictionary(DictionaryMeta dictionaryMeta, DictionaryLinks dictionaryLinks, DictionaryCepikData dictionaryCepikData) {
        this.dictionaryMeta = dictionaryMeta;
        this.dictionaryLinks = dictionaryLinks;
        this.dictionaryCepikData = dictionaryCepikData;
    }

    public Dictionary() {
    }

    public DictionaryMeta getDictionaryMeta() {
        return dictionaryMeta;
    }

    public DictionaryLinks getDictionaryLinks() {
        return dictionaryLinks;
    }

    public DictionaryCepikData getDictionaryCepikData() {
        return dictionaryCepikData;
    }
}
