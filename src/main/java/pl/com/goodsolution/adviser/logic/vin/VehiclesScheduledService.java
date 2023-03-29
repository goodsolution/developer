package pl.com.goodsolution.adviser.logic.vin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.vin.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehiclesScheduledService {
    private static final Logger log = LoggerFactory.getLogger(VehiclesScheduledService.class);
    private VehicleService vehicleService;
    private CepikService cepikService;

    public VehiclesScheduledService(VehicleService vehicleService, CepikService cepikService) {
        this.vehicleService = vehicleService;
        this.cepikService = cepikService;
    }

//    @Scheduled(fixedDelay = 1 * 15 * 1000)
    public void generateParametersForVehicles() {
        log.warn("start background sync: generate parameters");
        List<VehiclesParams> vehiclesParams = vehicleService.calculateVehiclesParams();
        for (VehiclesParams vehiclesParam : vehiclesParams) {
            List<VehiclesParams> vehicleParams = vehicleService.findVehicleParams(new VehiclesParamsFilter(
                    vehiclesParam.getVoivodeshipCode(),
                    vehiclesParam.getDateFrom(),
                    vehiclesParam.getDateTo()));
            if (vehicleParams.isEmpty()) {
                vehicleService.createVehicleParams(new VehiclesParams(
                        vehiclesParam.getVoivodeshipCode(),
                        null,
                        vehiclesParam.getDateFrom(),
                        vehiclesParam.getDateTo(),
                        vehiclesParam.getPage(),
                        vehiclesParam.getPageCheck()));
            }
        }
    }

//    @Scheduled(fixedDelay = 1 * 15 * 1000)
    public void generateParametersForVehiclesWithMoreThenOnePageAsParameter() {
        log.warn("start background sync: generate parameters with more then one page as parameter");
        try {
            VehiclesParams vehicleParamWithPageOne = vehicleService.getVehicleParamWithPageOne();
            List<VehiclesParams> vehiclesParams = vehicleService.calculateVehicleParamsWithPageParameterBiggerThenOne(vehicleParamWithPageOne);
            for (VehiclesParams vehiclesParam : vehiclesParams) {
                List<VehiclesParams> vehicleParams = vehicleService.findVehicleParams(new VehiclesParamsFilter(
                        vehiclesParam.getVoivodeshipCode(),
                        vehiclesParam.getDateFrom(),
                        vehiclesParam.getDateTo(),
                        vehiclesParam.getPage(),
                        vehiclesParam.getPageCheck()));
                if (vehicleParams.isEmpty()) {
                    vehicleService.createVehicleParams(vehiclesParam);
                }
            }
            vehicleService.updateVehicleParams(new VehiclesParams(
                    vehicleParamWithPageOne.getId(),
                    vehicleParamWithPageOne.getVoivodeshipCode(),
                    vehicleParamWithPageOne.getLastRefreshDateTime(),
                    vehicleParamWithPageOne.getDateFrom(),
                    vehicleParamWithPageOne.getDateTo(),
                    vehicleParamWithPageOne.getPage(),
                    LocalDateTime.now()));
        } catch (NoSuchElementException e) {
            // No processing
        }
    }

//    @Scheduled(fixedDelay = 1 * 15 * 1000)
    public void backgroundVehiclesSync() {
        log.warn("start background sync: vehicles sync");
        try {
            VehiclesParams vehicleParamToUpdate = vehicleService.getVehicleParamToUpdate();
            CarsTransformed vehicles = cepikService.findVehicles(new CarsFilter(
                    vehicleParamToUpdate.getVoivodeshipCode(),
                    vehicleParamToUpdate.getDateFrom(),
                    vehicleParamToUpdate.getDateTo()));
            for (CarTransformed car : vehicles.getCars()) {
                List<CarTransformed> vehiclesFromDB = vehicleService.findVehicles(new CarsFilter(car.getId()));
                if (vehiclesFromDB.isEmpty()) {
                    vehicleService.createVehicles(car.toUpdateVehicles());
                } else {
                    vehicleService.updateVehicles(car.toUpdateVehicles());
                }
            }
            vehicleService.updateVehicleParams(new VehiclesParams(
                    vehicleParamToUpdate.getId(),
                    vehicleParamToUpdate.getVoivodeshipCode(),
                    LocalDateTime.now(),
                    vehicleParamToUpdate.getDateFrom(),
                    vehicleParamToUpdate.getDateTo(),
                    vehicleParamToUpdate.getPage(),
                    vehicleParamToUpdate.getPageCheck()));
        } catch (NoSuchElementException e) {
            // No processing
        }
    }

//    @Scheduled(fixedDelay = 1 * 15 * 1000)
    public void backgroundVehicleSync() {
        log.warn("start background sync: vehicle sync");
        try {
            CarTransformed vehicle = cepikService.getVehicle(new CarsFilter(vehicleService.getVehicleIdToUpdate()));
            vehicleService.updateVehicle(vehicle.toUpdateVehicle());
        } catch (NoSuchElementException e) {
            //No processing
        }
    }

}
