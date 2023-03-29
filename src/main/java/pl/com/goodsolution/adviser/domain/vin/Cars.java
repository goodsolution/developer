package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cars {

    @JsonProperty("data")
    private List<CarData> carDataList;
    @JsonProperty("meta")
    private CarMeta carMeta;
    @JsonProperty("links")
    private CarLinks carLinks;

    public Cars(List<CarData> carDataList, CarMeta carMeta, CarLinks carLinks) {
        this.carDataList = carDataList;
        this.carMeta = carMeta;
        this.carLinks = carLinks;
    }

    public Cars() {
    }

    public List<CarData> getCarDataList() {
        return carDataList;
    }

    public CarMeta getCarMeta() {
        return carMeta;
    }

    public CarLinks getCarLinks() {
        return carLinks;
    }
}
