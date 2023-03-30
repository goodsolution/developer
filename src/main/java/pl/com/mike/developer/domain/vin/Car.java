package pl.com.mike.developer.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {
    @JsonProperty("data")
    private CarData carData;
    @JsonProperty("meta")
    private CarMeta carMeta;
    @JsonProperty("links")
    private CarLinks carLinks;

    public Car(CarData carData, CarMeta carMeta, CarLinks carLinks) {
        this.carData = carData;
        this.carMeta = carMeta;
        this.carLinks = carLinks;
    }

    public Car() {
    }

    public CarMeta getCarMeta() {
        return carMeta;
    }

    public CarData getCarData() {
        return carData;
    }

    public CarLinks getCarLinks() {
        return carLinks;
    }
}