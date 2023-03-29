package pl.com.goodsolution.adviser.domain.vin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarData {
    private Long id;
    private String type;
    @JsonProperty("attributes")
    private CarAttributes carAttributes;

    public CarData(Long id, String type, CarAttributes carAttributes) {
        this.id = id;
        this.type = type;
        this.carAttributes = carAttributes;
    }

    public CarData() {
    }

    public CarAttributes getCarAttributes() {
        return carAttributes;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
