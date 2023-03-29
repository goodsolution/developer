package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DictionaryCepikDataAttributes {

    @JsonProperty("dostepne-rekordy-slownika")
    private List<AvailableDictionaryRecords> availableDictionaryRecordsList;

    @JsonProperty("ilosc-rekordow-slownika")
    private Integer dictionaryRecordsQuantity;

    @JsonProperty("opis-slownika")
    private String dictionaryDescription;

    public DictionaryCepikDataAttributes(List<AvailableDictionaryRecords> availableDictionaryRecordsList, Integer dictionaryRecordsQuantity, String dictionaryDescription) {
        this.availableDictionaryRecordsList = availableDictionaryRecordsList;
        this.dictionaryRecordsQuantity = dictionaryRecordsQuantity;
        this.dictionaryDescription = dictionaryDescription;
    }

    public DictionaryCepikDataAttributes() {
    }

    public List<AvailableDictionaryRecords> getAvailableDictionaryRecordsList() {
        return availableDictionaryRecordsList;
    }

    public Integer getDictionaryRecordsQuantity() {
        return dictionaryRecordsQuantity;
    }

    public String getDictionaryDescription() {
        return dictionaryDescription;
    }
}
