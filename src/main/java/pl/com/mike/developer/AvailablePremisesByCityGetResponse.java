package pl.com.mike.developer;

import java.util.List;

public class AvailablePremisesByCityGetResponse {

    private List<AvailablePremiseByCityGetResponse> availablePremisesByCity;

    public AvailablePremisesByCityGetResponse(List<AvailablePremiseByCityGetResponse> availablePremisesByCity) {
        this.availablePremisesByCity = availablePremisesByCity;
    }

    public List<AvailablePremiseByCityGetResponse> getAvailablePremisesByCity() {
        return availablePremisesByCity;
    }

    public void setAvailablePremisesByCity(List<AvailablePremiseByCityGetResponse> availablePremisesByCity) {
        this.availablePremisesByCity = availablePremisesByCity;
    }
}
