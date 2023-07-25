package pl.com.mike.developer;

import java.util.List;

public class InvestmentCitiesByDeveloperGetResponse {
    List<String> cities;

    public InvestmentCitiesByDeveloperGetResponse(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
