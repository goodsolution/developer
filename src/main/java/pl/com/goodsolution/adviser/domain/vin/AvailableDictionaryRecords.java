package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableDictionaryRecords {
    @JsonProperty("klucz-slownika")
    private String code;
    @JsonProperty("wartosc-slownika")
    private String value;
    @JsonProperty("liczba-wystapien")
    private Integer numberOfAppearancesy;

    public AvailableDictionaryRecords(String dictionaryKey, String dictionaryValue, Integer numberOfAppearancesy) {
        this.code = dictionaryKey;
        this.value = dictionaryValue;
        this.numberOfAppearancesy = numberOfAppearancesy;
    }

    public AvailableDictionaryRecords() {
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public Integer getNumberOfAppearancesy() {
        return numberOfAppearancesy;
    }
}
