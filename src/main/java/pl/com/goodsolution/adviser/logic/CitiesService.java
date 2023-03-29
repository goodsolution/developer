package pl.com.goodsolution.adviser.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.CityData;
import pl.com.goodsolution.adviser.domain.CityDiscountData;

import java.time.LocalDate;

@Service
@Transactional
public class CitiesService {
    private CitiesJdbcRepository citiesJdbcRepository;

    public CitiesService(CitiesJdbcRepository citiesJdbcRepository) {
        this.citiesJdbcRepository = citiesJdbcRepository;
    }

    //TODO requires new
    public void addFailedCityCheck(String city) {
        citiesJdbcRepository.addFailedCityCheck(city);
    }

    public CityData getCity(Long cityId) {
        return citiesJdbcRepository.getCity(cityId);
    }

    public CityDiscountData getCityDiscount(Long cityId) {
        CityData city = getCity(cityId);
        if (city.getDiscount()) {
            if (city.getDateTo() != null && !city.getDateTo().isBefore(LocalDate.now())) {
                return new CityDiscountData(cityId, city.getDateTo(), city.getDiscountValue());
            }
            return null;
        }
        return null;
    }

    public boolean isNewCity(String city) {
        return false;
    }
}
