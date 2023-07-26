package pl.com.mike.developer;

import pl.com.mike.developer.domain.developer.CityData;

import java.util.List;

public class InvestmentCitiesByDeveloperGetResponse {
    List<CityData> cities;

    public InvestmentCitiesByDeveloperGetResponse(List<CityData> cities) {
        this.cities = cities;
    }

    public List<CityData> getCities() {
        return cities;
    }

    public void setCities(List<CityData> cities) {
        this.cities = cities;
    }
}
