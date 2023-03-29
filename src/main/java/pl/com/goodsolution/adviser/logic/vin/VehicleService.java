package pl.com.goodsolution.adviser.logic.vin;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.vin.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    private VehiclesRepository vehiclesRepository;
    private CepikService cepikService;
    private static final Integer YEARS_BACK = 5;
    public static final Integer LIMIT_RESULTS_PER_ONE_PAGE = 500;

    public VehicleService(VehiclesRepository vehiclesRepository, CepikService cepikService) {
        this.vehiclesRepository = vehiclesRepository;
        this.cepikService = cepikService;
    }

    public Long getVehicleIdToUpdate() {
        return vehiclesRepository.getVehicleCepikIdToUpdate();
    }

    public VehiclesParams getVehicleParamToUpdate() {
        return vehiclesRepository.getVehicleParamToUpdate();
    }

    public List<VehiclesParams> calculateVehiclesParams() {
        List<VehiclesParams> vehiclesParams = new ArrayList<>();
        YearMonth dateFromPast = YearMonth.now().minusYears(YEARS_BACK);
        long monthsBetween = calculateMonthsBetween(dateFromPast);
        for (String voivodeshipCode : getVoivodeshipCodes()) {
            for (int i = 0; i < monthsBetween; i++) {
                vehiclesParams.add(new VehiclesParams(voivodeshipCode,
                        getDateWithFirstDayOfMonth(dateFromPast, i),
                        getDateWithLastDayOfMonth(dateFromPast, i),
                        1,
                        null));
            }
        }
        return vehiclesParams;
    }

    public List<VehiclesParams> calculateVehicleParamsWithPageParameterBiggerThenOne(VehiclesParams vehiclesParams) {
        List<VehiclesParams> vehiclesParamsWithPageParameterBiggerThanOne = new ArrayList<>();
        if (vehiclesParams.getPage() == 1) {
            CarsTransformed vehicles = getCarsTransformed(vehiclesParams);
            int numberOfPages = getNumberOfPages(vehicles);
            for (int i = 2; i <= numberOfPages; i++) {
                vehiclesParamsWithPageParameterBiggerThanOne.add(new VehiclesParams(
                        vehiclesParams.getVoivodeshipCode(),
                        vehiclesParams.getDateFrom(),
                        vehiclesParams.getDateTo(),
                        i));
            }
        }
        return vehiclesParamsWithPageParameterBiggerThanOne;
    }

    private static Integer getNumberOfPages(CarsTransformed vehicles) {
        return vehicles.getCars().get(0).getCount() / LIMIT_RESULTS_PER_ONE_PAGE;
    }

    private CarsTransformed getCarsTransformed(VehiclesParams vehiclesParams) {
        return cepikService.findVehicles(new CarsFilter(vehiclesParams.getVoivodeshipCode(), vehiclesParams.getDateFrom(), vehiclesParams.getDateTo()));
    }

    public VehiclesParams getVehicleParamWithPageOne() {
        return vehiclesRepository.getVehicleParamWithPageValueOne();
    }

    private static LocalDate getDateWithLastDayOfMonth(YearMonth dateFromPast, int i) {
        return dateFromPast.plusMonths(i).atEndOfMonth();
    }

    private static LocalDate getDateWithFirstDayOfMonth(YearMonth dateFromPast, int i) {
        return dateFromPast.plusMonths(i).atDay(1);
    }

    private static long calculateMonthsBetween(YearMonth dateFromPast) {
        return Math.abs(ChronoUnit.MONTHS.between(YearMonth.now(), dateFromPast));
    }

    private List<String> getVoivodeshipCodes() {
        List<String> voivodeshipCodes = new ArrayList<>();
        for (int i = 0; i < cepikService.getVoivodeshipDictionaryTransformed().size(); i++) {
            voivodeshipCodes.add(cepikService.getVoivodeshipDictionaryTransformed().get(i).getCode());
        }
        return voivodeshipCodes;
    }

    public void createVehicleParams(VehiclesParams vehiclesParams) {
        vehiclesRepository.createVehiclesParams(vehiclesParams);
    }

    public List<VehiclesParams> findVehicleParams(VehiclesParamsFilter vehiclesParamsFilter) {
        return vehiclesRepository.findVehicleParams(vehiclesParamsFilter);
    }

    public void updateVehicleParams(VehiclesParams vehiclesParams) {
        vehiclesRepository.updateVehicleParams(vehiclesParams);
    }

    public List<CarTransformed> findVehicles(CarsFilter filter) {
        return vehiclesRepository.findVehicles(filter);
    }

    public CarsTransformed findCars(CarsFilter filter) {
        return new CarsTransformed(vehiclesRepository.findVehicles(filter));
    }

    public void updateVehicle(UpdateVehicle updateVehicle) {
        vehiclesRepository.updateVehicle(updateVehicle);
    }

    public void updateVehicles(UpdateVehicles updateVehicles) {
        vehiclesRepository.updateVehicles(updateVehicles);
    }

    public void createVehicles(UpdateVehicles updateVehicles) {
        vehiclesRepository.createVehicles(updateVehicles);
    }

}

