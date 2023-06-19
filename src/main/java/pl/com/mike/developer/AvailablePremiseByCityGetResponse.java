package pl.com.mike.developer;

public class AvailablePremiseByCityGetResponse {

    private String city;
    private Long availablePremisesNumber;

    public AvailablePremiseByCityGetResponse(String city, Long availablePremisesNumber) {
        this.city = city;
        this.availablePremisesNumber = availablePremisesNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getAvailablePremisesNumber() {
        return availablePremisesNumber;
    }

    public void setAvailablePremisesNumber(Long availablePremisesNumber) {
        this.availablePremisesNumber = availablePremisesNumber;
    }
}
