package pl.com.goodsolution.adviser.domain.vin;

import java.util.List;

public class CarsTransformed {
    private List<CarTransformed> cars;

    public CarsTransformed(List<CarTransformed> carsTransformed) {
        this.cars = carsTransformed;
    }

    public List<CarTransformed> getCars() {
        return cars;
    }

}
