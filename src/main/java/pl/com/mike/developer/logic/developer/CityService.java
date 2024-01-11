package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.developer.CityData;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final ApplicationConfig applicationConfig;

    public CityService(CityRepository cityRepository, ApplicationConfig applicationConfig) {
        this.cityRepository = cityRepository;
        this.applicationConfig = applicationConfig;
    }

    public List<CityData> getCities() {
        return cityRepository.findAll();
    }

    public List<CityData> getCitiesByDeveloperCode() {
        return cityRepository.getCitiesByDeveloperCode(applicationConfig.getSystemCode());
    }

}
