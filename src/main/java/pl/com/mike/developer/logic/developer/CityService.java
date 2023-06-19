package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.CityDataFilter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    public List<String> getCitiesByDeveloperId(CityDataFilter cityDataFilter) {
        return Optional.of(cityRepository.getCitiesByDeveloperId(cityDataFilter.getId()))
                .orElseThrow(() -> new NoSuchElementException("no such city"));
    }

}
