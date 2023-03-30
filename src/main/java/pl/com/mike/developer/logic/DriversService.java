package pl.com.mike.developer.logic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.CityData;
import pl.com.mike.developer.domain.DriverData;
import pl.com.mike.developer.domain.DriverPayData;
import pl.com.mike.developer.domain.DriverSettlementCreateData;

import java.time.LocalDate;

@Service
@Transactional
public class DriversService {
    private CitiesService citiesService;
    private DriversJdbcRepository driversJdbcRepository;

    public DriversService(CitiesService citiesService, DriversJdbcRepository driversJdbcRepository) {
        this.citiesService = citiesService;
        this.driversJdbcRepository = driversJdbcRepository;
    }

    public DriverData getDefaultDriverForCity(Long cityId) {
        CityData city = citiesService.getCity(cityId);
        if (city == null) {
            throw new IllegalArgumentException("Nie mogę pobrać miasta o id: " + cityId);
        }
        Long driverId;
        if (city.getDefaultDriverId().longValue() == 0L) {
            driverId = getActiveDriverForCity(cityId);
            if(driverId == null) {
                throw new IllegalArgumentException("Nie można pobrać aktywnego kierowcy dla miasta o id: " + cityId);
            }
        } else {
            DriverData driver = getActiveDriver(city.getDefaultDriverId());
            if (driver != null) {
                return driver;
            } else {
                driverId = getActiveDriverForCity(cityId);
            }
        }
        return getActiveDriver(driverId);
    }

    public DriverData getDriver(Long id) {
        return driversJdbcRepository.getDriver(id);
    }

    public DriverData getActiveDriver(Long id) {
        return driversJdbcRepository.getActiveDriver(id);
    }

    public DriverPayData getAndCreateDriverPay(Long driverId) {
        if (driversJdbcRepository.getPayConfigForDriverCount(driverId).equals(0L)) {
            driversJdbcRepository.createPayConfigForDriver(driverId);
        }
        return driversJdbcRepository.getPayConfigForDriver(driverId);
    }

    private Long getActiveDriverForCity(Long cityId) {
        return driversJdbcRepository.getActiveDriverForCity(cityId);
    }

    public Long getDriverIdForOrder(Long orderId) {
        return driversJdbcRepository.getDriverIdForOrder(orderId);
    }

    public Long getDriverSettlementCountFor(Long driverId, LocalDate date, String address) {
        return driversJdbcRepository.getDriverSettlementCountFor(driverId, date, address);
    }

    public Long getDriverSettlementForDeliveryCount(Long deliveryId) {
        return driversJdbcRepository.getDriverSettlementForDeliveryCount(deliveryId);
    }

    public void createDriverSettlement(DriverSettlementCreateData data) {
        driversJdbcRepository.createDriverSettlement(data);
    }
}
