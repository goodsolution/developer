package pl.com.mike.developer.logic.vin;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.vin.*;
import pl.com.mike.developer.logic.CacheType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CepikService {
    private CepikApiRepository cepikApiRepository;
    private VehiclesRepository vehiclesRepository;

    public CepikService(CepikApiRepository cepikApiRepository, VehiclesRepository vehiclesRepository) {
        this.cepikApiRepository = cepikApiRepository;
        this.vehiclesRepository = vehiclesRepository;
    }

    public Dictionaries getDictionaries(DictionariesFilter filter) {
        return cepikApiRepository.getDictionaries(filter);
    }

    public Optional<Dictionary> getDictionary(DictionariesFilter filter) {
        return cepikApiRepository.getDictionary(filter);
    }

    @Cacheable(CacheType.VEHICLES)
    public List<AvailableDictionaryRecords> getVoivodeshipDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryVoivodeship().isPresent()) {
            return cepikApiRepository.getDictionaryVoivodeship().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No voivodship found");
    }

    public List<AvailableDictionaryRecords> getBrandDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryBrand().isPresent()) {
            return cepikApiRepository.getDictionaryBrand().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No brand found");
    }

    public List<AvailableDictionaryRecords> getVehicleKindDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryVehicleKind().isPresent()) {
            return cepikApiRepository.getDictionaryVehicleKind().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No vehicle kind found");
    }

    public List<AvailableDictionaryRecords> getVehicleOriginDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryVehicleOrigin().isPresent()) {
            return cepikApiRepository.getDictionaryVehicleOrigin().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No vehicle origin found");
    }

    public List<AvailableDictionaryRecords> getVehicleProductionMethodDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryVehicleProductionMethod().isPresent()) {
            return cepikApiRepository.getDictionaryVehicleProductionMethod().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No vehicle production method found");
    }

    public List<AvailableDictionaryRecords> getVehicleFuelKindDictionaryTransformed() {
        if (cepikApiRepository.getDictionaryVehicleFuelKind().isPresent()) {
            return cepikApiRepository.getDictionaryVehicleFuelKind().get().getDictionaryCepikData().getDictionaryCepikDataAttributes().getAvailableDictionaryRecordsList();
        } else throw new IllegalArgumentException("No vehicle fuel kind method found");
    }

    public CarTransformed getVehicle(CarsFilter filter) {
        validateCar(filter.getCepikId());
        if (cepikApiRepository.get(filter.getCepikId()).isPresent()) {
            Car car = cepikApiRepository.get(filter.getCepikId()).get();
            return new CarTransformed(car.getCarData(), car.getCarData().getCarAttributes(), filter.getVin(), car.getCarMeta());
        } else {
            throw new IllegalArgumentException("No vehicle found");
        }
    }

    public CarsTransformed findVehicles(CarsFilter filter) {
        List<CarTransformed> carsTransformed = new ArrayList<>();
        validateCarsFilter(filter);
        if (cepikApiRepository.find(filter).isPresent()) {
            Cars cars = cepikApiRepository.find(filter).get();
            List<CarData> carDataList = cars.getCarDataList();
            for (CarData carData : carDataList) {
                CarAttributes carAttributes = carData.getCarAttributes();
                CarMeta carMeta = cars.getCarMeta();
                CarTransformed carTransformed = new CarTransformed(carData, carAttributes, generateRandomVin(), carMeta);
                carsTransformed.add(carTransformed);
            }
        } else {
            throw new IllegalArgumentException("No vehicles found");
        }
        return new CarsTransformed(carsTransformed);
    }

    private void validateCarsFilter(CarsFilter filter) {
        if (filter.getVoivodeshipCode() == null) {
            throw new IllegalArgumentException("VoivodeshipCode is mandatory");
        }
    }

    private void validateCar(java.lang.Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
    }

    private String generateRandomVin() {
        return "xxx";
    }

}
