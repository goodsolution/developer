package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Dictionaries {
    @JsonProperty("meta")
    private DictionaryMeta dictionaryMeta;
    @JsonProperty("links")
    private DictionaryLinks dictionaryLinks;
    @JsonProperty("data")
    private List<DictionaryCepikData> dictionaryCepikDataList;

    public Dictionaries(DictionaryMeta dictionaryMeta, DictionaryLinks dictionaryLinks, List<DictionaryCepikData> dictionaryCepikDataList) {
        this.dictionaryMeta = dictionaryMeta;
        this.dictionaryLinks = dictionaryLinks;
        this.dictionaryCepikDataList = dictionaryCepikDataList;
    }

    public Dictionaries() {
    }

    public DictionaryMeta getDictionaryMeta() {
        return dictionaryMeta;
    }

    public DictionaryLinks getDictionaryLinks() {
        return dictionaryLinks;
    }

    public List<DictionaryCepikData> getDictionaryCepikDataList() {
        return dictionaryCepikDataList;
    }
}
