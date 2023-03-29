package pl.com.goodsolution.adviser.logic.vin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.vin.*;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;

import java.time.LocalDateTime;
import java.util.*;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class VehiclesRepository {
    private JdbcTemplate jdbcTemplate;
    private CommonJdbcRepository commonJdbcRepository;

    public VehiclesRepository(JdbcTemplate jdbcTemplate, CommonJdbcRepository commonJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    public Long getVehicleCepikIdToUpdate() {
        String query = "SELECT * FROM vin_vehicles WHERE (last_refresh_date_time <= NOW() - INTERVAL 1 DAY OR last_refresh_date_time IS NULL) LIMIT 1;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map<String, Object> row : rows) {
            return getLong(row, "vech_id");
        }
        throw new NoSuchElementException("No vehicles to update found");
    }

    public VehiclesParams getVehicleParamToUpdate() {
        List<VehiclesParams> vehiclesParams = new ArrayList<>();
        String query = "SELECT * FROM vin_vehicles_params WHERE (last_refresh_date_time <= NOW() - INTERVAL 1 DAY OR last_refresh_date_time IS NULL) limit 1";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map<String, Object> row : rows) {
            vehiclesParams.add(new VehiclesParams(
                    getLong(row, "id"),
                    getString(row, "voivodeship_code"),
                    getDateTime(row, "last_refresh_date_time"),
                    getDateTime(row, "date_from").toLocalDate(),
                    getDateTime(row, "date_to").toLocalDate(),
                    getInteger(row, "page"),
                    getDateTime(row, "generating_params_date_time")
            ));
        }
        if (vehiclesParams.isEmpty()) {
            throw new NoSuchElementException();
        }
        return vehiclesParams.get(0);
    }


    public VehiclesParams getVehicleParamWithPageValueOne() {
        List<VehiclesParams> vehiclesParams = new ArrayList<>();
        String query = "SELECT * FROM vin_vehicles_params WHERE page = '1' AND generating_params_date_time is null limit 1;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map<String, Object> row : rows) {
            vehiclesParams.add(new VehiclesParams(
                    getLong(row, "id"),
                    getString(row, "voivodeship_code"),
                    getDateTime(row, "last_refresh_date_time"),
                    getDateTime(row, "date_from").toLocalDate(),
                    getDateTime(row, "date_to").toLocalDate(),
                    getInteger(row, "page"),
                    getDateTime(row, "generating_params_date_time")
            ));
        }
        if (vehiclesParams.isEmpty()) {
            throw new NoSuchElementException();
        }
        return vehiclesParams.get(0);
    }

    Long createVehiclesParams(VehiclesParams vehiclesParams) {
        String query = "INSERT INTO vin_vehicles_params (last_refresh_date_time, voivodeship_code, date_from, date_to, page, generating_params_date_time)  VALUES (?,?,?,?,?,?);";
        jdbcTemplate.update(query,
                vehiclesParams.getLastRefreshDateTime(),
                vehiclesParams.getVoivodeshipCode(),
                vehiclesParams.getDateFrom(),
                vehiclesParams.getDateTo(),
                vehiclesParams.getPage(),
                vehiclesParams.getPageCheck());
        return commonJdbcRepository.getLastInsertedId();
    }

    void updateVehicleParams(VehiclesParams vehiclesParams) {
        String query = "update vin_vehicles_params set voivodeship_code = ?, last_refresh_date_time = ?, date_from = ?, date_to = ?, page = ?, generating_params_date_time = ? where id = ?;";
        jdbcTemplate.update(query,
                vehiclesParams.getVoivodeshipCode(),
                vehiclesParams.getLastRefreshDateTime(),
                vehiclesParams.getDateFrom(),
                vehiclesParams.getDateTo(),
                vehiclesParams.getPage(),
                vehiclesParams.getPageCheck(),
                vehiclesParams.getId());
    }

    List<CarTransformed> findVehicles(CarsFilter filter) {
        List<CarTransformed> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vin_vehicles";
        if (filter != null) {
            query += " WHERE 1=1";
            if (filter.getCepikId() != null) {
                query += " AND vech_id = " + "'" + filter.getCepikId() + "'";
            }
            if (filter.getVoivodeshipCode() != null) {
                query += " AND vehicle_voivodeship_code = " + "'" + filter.getVoivodeshipCode() + "'";
            }
            if (filter.getDateType() != null) {
                query += " AND vehicle_date_type = " + "'" + filter.getDateType() + "'";
            }
            if (filter.getVin() != null) {
                query += " AND vehicle_vin = " + "'" + filter.getVin() + "'";
            }
            if (filter.getRegistered() != null) {
                query += " AND vehicle_is_registered = " + "'" + filter.getRegistered() + "'";
            }
            if (filter.getRegistered() != null) {
                query += " AND vehicle_is_registered = " + "'" + filter.getRegistered() + "'";
            }
            if (filter.getAreShownAllFields() != null) {
                query += " AND vehicle_are_shown_al_fields = " + "'" + filter.getAreShownAllFields() + "'";
            }
            if (filter.getFields() != null) {
                query += " AND vehicle_fields = " + "'" + Arrays.toString(filter.getFields()) + "'";
            }
            if (filter.getLimit() != null) {
                query += " AND vehicle_limit = " + "'" + filter.getLimit() + "'";
            }
            if (filter.getPage() != null) {
                query += " AND vehicle_page = " + "'" + filter.getPage() + "'";
            }
            if (filter.getSort() != null) {
                query += " AND vehicle_page = " + "'" + Arrays.toString(filter.getSort()) + "'";
            }
            if (filter.getDateOfFirstRegistration() != null) {
                query += " AND vehicle_date_first_registration = " + "'" + filter.getDateOfFirstRegistration() + "'";
            }
            if (filter.getBrand() != null) {
                query += " AND vehicle_brand = " + "'" + filter.getBrand() + "'";
            }
            if (filter.getModel() != null) {
                query += " AND vehicle_model = " + "'" + filter.getModel() + "'";
            }
            if (filter.getCarCategory() != null) {
                query += " AND vehicle_category = " + "'" + filter.getCarCategory() + "'";
            }
            if (filter.getType() != null) {
                query += " AND vehicle_type = " + "'" + filter.getType() + "'";
            }
            if (filter.getVariant() != null) {
                query += " AND vehicle_variant = " + "'" + filter.getVariant() + "'";
            }
            if (filter.getVersion() != null) {
                query += " AND vehicle_version = " + "'" + filter.getVersion() + "'";
            }
            if (filter.getCarKind() != null) {
                query += " AND vehicle_kind = " + "'" + filter.getCarKind() + "'";
            }
            if (filter.getCarSubKind() != null) {
                query += " AND vehicle_subtype = " + "'" + filter.getCarSubKind() + "'";
            }
            if (filter.getCarPurpose() != null) {
                query += " AND vehicle_destination = " + "'" + filter.getCarPurpose() + "'";
            }
            if (filter.getCarOrigin() != null) {
                query += " AND vehicle_origin = " + "'" + filter.getCarOrigin() + "'";
            }
            if (filter.getCarNamePlateKind() != null) {
                query += " AND vehicle_nameplate_type = " + "'" + filter.getCarNamePlateKind() + "'";
            }
            if (filter.getCarProductionMethod() != null) {
                query += " AND vehicle_method_of_production = " + "'" + filter.getCarProductionMethod() + "'";
            }
            if (filter.getCarProductionYear() != null) {
                query += " AND vehicle_year_of_production = " + "'" + filter.getCarProductionYear() + "'";
            }
            if (filter.getInitialRegistrationDateInCountry() != null) {
                query += " AND vehicle_date_first_registration_in_country = " + "'" + filter.getInitialRegistrationDateInCountry() + "'";
            }
            if (filter.getLastRegistrationDateInCountry() != null) {
                query += " AND vehicle_date_last_registration_in_country = " + "'" + filter.getLastRegistrationDateInCountry() + "'";
            }
            if (filter.getRegistrationDateAbroad() != null) {
                query += " AND vehicle_date_first_registration_abroad = " + "'" + filter.getRegistrationDateAbroad() + "'";
            }
            if (filter.getEngineDisplacement() != null) {
                query += " AND vehicle_engine_displacement = " + "'" + filter.getEngineDisplacement() + "'";
            }
            if (filter.getMotorPowerToUnladenWeightRatioMotorcycle() != null) {
                query += " AND vehicle_motor_power_to_weight_ratio_motorcycles = " + "'" + filter.getMotorPowerToUnladenWeightRatioMotorcycle() + "'";
            }
            if (filter.getEngineNetPower() != null) {
                query += " AND vehicle_engine_net_power = " + "'" + filter.getEngineNetPower() + "'";
            }
            if (filter.getHybridEngineNetPower() != null) {
                query += " AND vehicle_max_net_power_of_engine_hybrid = " + "'" + filter.getHybridEngineNetPower() + "'";
            }
            if (filter.getOwnMass() != null) {
                query += " AND vehicle_own_mass = " + "'" + filter.getOwnMass() + "'";
            }
            if (filter.getMassOfVehicleReadyToRun() != null) {
                query += " AND vehicle_mass_of_ready_to_run = " + "'" + filter.getMassOfVehicleReadyToRun() + "'";
            }
            if (filter.getPermissibleGrossWeight() != null) {
                query += " AND vehicle_permissible_gross_weight = " + "'" + filter.getPermissibleGrossWeight() + "'";
            }
            if (filter.getMaxTotalMass() != null) {
                query += " AND vehicle_maximum_total_mass = " + "'" + filter.getMaxTotalMass() + "'";
            }
            if (filter.getPermissiblePackage() != null) {
                query += " AND vehicle_allowed_package = " + "'" + filter.getPermissiblePackage() + "'";
            }
            if (filter.getMaxPackage() != null) {
                query += " AND vehicle_maximum_payload = " + "'" + filter.getMaxPackage() + "'";
            }
            if (filter.getPermissibleTotalMassOfVehiclesSet() != null) {
                query += " AND vehicle_permissible_total_weight_of_vehicle_combinations = " + "'" + filter.getPermissibleTotalMassOfVehiclesSet() + "'";
            }
            if (filter.getAxlesNumber() != null) {
                query += " AND vehicle_number_of_axles = " + "'" + filter.getAxlesNumber() + "'";
            }
            if (filter.getPermissibleAxleLoad() != null) {
                query += " AND vehicle_allowable_axle_load = " + "'" + filter.getPermissibleAxleLoad() + "'";
            }
            if (filter.getMaxAxleLoad() != null) {
                query += " AND vehicle_maximum_axle_weight = " + "'" + filter.getMaxAxleLoad() + "'";
            }
            if (filter.getMaxTotalWeightTrailerWithBrake() != null) {
                query += " AND vehicle_max_total_weight_of_trailed_trailer_without_brake = " + "'" + filter.getMaxTotalWeightTrailerWithBrake() + "'";
            }
            if (filter.getMaxTotalWeightTrailerWithoutBrake() != null) {
                query += " AND vehicle_max_total_weight_trailer_without_brake = " + "'" + filter.getMaxTotalWeightTrailerWithoutBrake() + "'";
            }
            if (filter.getPlacesNumberInTotal() != null) {
                query += " AND vehicle_number_seats_total = " + "'" + filter.getPlacesNumberInTotal() + "'";
            }
            if (filter.getSeatsNumber() != null) {
                query += " AND vehicle_number_of_seats = " + "'" + filter.getSeatsNumber() + "'";
            }
            if (filter.getStandingPlacesNumber() != null) {
                query += " AND vehicle_number_of_standing_places = " + "'" + filter.getStandingPlacesNumber() + "'";
            }
            if (filter.getFuelType() != null) {
                query += " AND vehicle_type_of_fuel = " + "'" + filter.getFuelType() + "'";
            }
            if (filter.getFirstAlternativefuelType() != null) {
                query += " AND vehicle_kind_of_first_alternative_fuel = " + "'" + filter.getFirstAlternativefuelType() + "'";
            }
            if (filter.getSecondAlternativefuelType() != null) {
                query += " AND vehicle_kind_of_second_alternative_fuel = " + "'" + filter.getSecondAlternativefuelType() + "'";
            }
            if (filter.getAverageFuelConsumption() != null) {
                query += " AND vehicle_avg_fuel_consumption = " + "'" + filter.getAverageFuelConsumption() + "'";
            }
            if (filter.getCo2EmissionLevel() != null) {
                query += " AND vehicle_co2_emission_level = " + "'" + filter.getCo2EmissionLevel() + "'";
            }
            if (filter.getSuspensionType() != null) {
                query += " AND vehicle_kind_of_suspensions = " + "'" + filter.getSuspensionType() + "'";
            }
            if (filter.getEquipmentAndTypeOfRadarDevice() != null) {
                query += " AND vehicle_equipment_and_type_of_radar_device = " + "'" + filter.getEquipmentAndTypeOfRadarDevice() + "'";
            }
            if (filter.getSteeringRightHandSide() != null) {
                query += " AND vehicle_steering_right_hand_side = " + "'" + filter.getSteeringRightHandSide() + "'";
            }
            if (filter.getSteeringRightHandSideOrigin() != null) {
                query += " AND vehicle_steering_right_side_originally = " + "'" + filter.getSteeringRightHandSideOrigin() + "'";
            }
            if (filter.getCatalystAbsorber() != null) {
                query += " AND vehicle_catalyst_absorber = " + "'" + filter.getCatalystAbsorber() + "'";
            }
            if (filter.getManufacturerName() != null) {
                query += " AND vehicle_manufacturer_name = " + "'" + filter.getManufacturerName() + "'";
            }
            if (filter.getCarTransportInstituteCode() != null) {
                query += " AND vehicle_car_transport_institute_code = " + "'" + filter.getCarTransportInstituteCode() + "'";
            }
            if (filter.getWheelBaseSteeringOtherAxles() != null) {
                query += " AND vehicle_wheel_base_steering_other_axles = " + "'" + filter.getWheelBaseSteeringOtherAxles() + "'";
            }
            if (filter.getMaxTrackWheel() != null) {
                query += " AND vehicle_maximum_track_width = " + "'" + filter.getMaxTrackWheel() + "'";
            }
            if (filter.getAvgTrackWheel() != null) {
                query += " AND vehicle_maximum_track_width = " + "'" + filter.getAvgTrackWheel() + "'";
            }
            if (filter.getMinTrackWheel() != null) {
                query += " AND vehicle_min_track_width = " + "'" + filter.getMinTrackWheel() + "'";
            }
            if (filter.getExhaustEmissionReduction() != null) {
                query += " AND vehicle_exhaust_emission_reduction = " + "'" + filter.getExhaustEmissionReduction() + "'";
            }
            if (filter.getEncodingTypeSubtypeDestiny() != null) {
                query += " AND vehicle_type_encoding_kind_subtype_destiny = " + "'" + filter.getEncodingTypeSubtypeDestiny() + "'";
            }
            if (filter.getCodeKindSubgenusDestiny() != null) {
                query += " AND vehicle_code_kind_subgenus_destiny = " + "'" + filter.getCodeKindSubgenusDestiny() + "'";
            }
            if (filter.getVehicleDeregistrationDate() != null) {
                query += " AND vehicle_date_of_deregistration = " + "'" + filter.getVehicleDeregistrationDate() + "'";
            }
            if (filter.getVehicleDeregistrationReason() != null) {
                query += " AND vehicle_reason_for_deregistration_of_vehicle = " + "'" + filter.getVehicleDeregistrationReason() + "'";
            }
            if (filter.getDataInputDate() != null) {
                query += " AND vehicle_date_data_input = " + "'" + filter.getDataInputDate() + "'";
            }
            if (filter.getRegistrationVoivodship() != null) {
                query += " AND vehicle_registration_voivodeship = " + "'" + filter.getRegistrationVoivodship() + "'";
            }
            if (filter.getRegistrationCommune() != null) {
                query += " AND vehicle_registration_community = " + "'" + filter.getRegistrationCommune() + "'";
            }
            if (filter.getRegistrationDistrict() != null) {
                query += " AND vehicle_registration_country = " + "'" + filter.getRegistrationDistrict() + "'";
            }
            if (filter.getOwnerVoivodship() != null) {
                query += " AND vehicle_owner_voivodeship = " + "'" + filter.getOwnerVoivodship() + "'";
            }
            if (filter.getOwnerDistrict() != null) {
                query += " AND vehicle_owner_district = " + "'" + filter.getOwnerDistrict() + "'";
            }
            if (filter.getOwnerCommune() != null) {
                query += " AND vehicle_owner_commune = " + "'" + filter.getOwnerCommune() + "'";
            }
            if (filter.getCo2EmissionsAlternativeFuel1() != null) {
                query += " AND vehicle_co2_emission_level_first_fuel_alternative = " + "'" + filter.getCo2EmissionsAlternativeFuel1() + "'";
            }
            if (filter.getDateFrom() != null && filter.getDateTo() != null) {
                query += " AND vehicle_date_first_registration_in_country BETWEEN " + "'" + filter.getDateFrom() + "'" + " AND " + "'" + filter.getDateTo() + "'";
            }
            query += preparePaginationQuery(filter.getPages(), filter.getPageSize());
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        for (Map<String, Object> row : rows) {
            vehicles.add(new CarTransformed(
                    getLong(row, "vech_id"),
                    getString(row, "vehicle_type"),
                    getString(row, "vehicle_brand"),
                    getString(row, "vehicle_model"),
                    getString(row, "vehicle_kind"),
                    getString(row, "vehicle_date_first_registration"),
                    getString(row, "vehicle_vin"),
                    getString(row, "vehicle_category"),
                    getString(row, "vehicle_variant"),
                    getString(row, "vehicle_version"),
                    getString(row, "vehicle_subtype"),
                    getString(row, "vehicle_destination"),
                    getString(row, "vehicle_origin"),
                    getString(row, "vehicle_nameplate_type"),
                    getString(row, "vehicle_method_of_production"),
                    getString(row, "vehicle_year_of_production"),
                    getString(row, "vehicle_date_first_registration_in_country"),
                    getString(row, "vehicle_date_last_registration_in_country"),
                    getString(row, "vehicle_date_first_registration_abroad"),
                    getDouble(row, "vehicle_engine_displacement"),
                    getDouble(row, "vehicle_motor_power_to_weight_ratio_motorcycles"),
                    getDouble(row, "vehicle_engine_net_power"),
                    getDouble(row, "vehicle_max_net_power_of_engine_hybrid"),
                    getDouble(row, "vehicle_own_mass"),
                    getDouble(row, "vehicle_mass_of_ready_to_run"),
                    getDouble(row, "vehicle_permissible_gross_weight"),
                    getDouble(row, "vehicle_maximum_total_mass"),
                    getDouble(row, "vehicle_allowed_package"),
                    getDouble(row, "vehicle_maximum_payload"),
                    getDouble(row, "vehicle_permissible_total_weight_of_vehicle_combinations"),
                    getDouble(row, "vehicle_number_of_axles"),
                    getDouble(row, "vehicle_allowable_axle_load"),
                    getDouble(row, "vehicle_maximum_axle_weight"),
                    getDouble(row, "vehicle_max_total_weight_of_trailed_trailer_without_brake"),
                    getDouble(row, "vehicle_max_total_weight_trailer_without_brake"),
                    getInteger(row, "vehicle_number_seats_total"),
                    getInteger(row, "vehicle_number_of_seats"),
                    getInteger(row, "vehicle_number_of_standing_places"),
                    getString(row, "vehicle_type_of_fuel"),
                    getString(row, "vehicle_kind_of_first_alternative_fuel"),
                    getString(row, "vehicle_kind_of_second_alternative_fuel"),
                    getDouble(row, "vehicle_avg_fuel_consumption"),
                    getDouble(row, "vehicle_co2_emission_level"),
                    getString(row, "vehicle_kind_of_suspensions"),
                    getString(row, "vehicle_equipment_and_type_of_radar_device"),
                    getBoolean(row, "vehicle_hook"),
                    getBoolean(row, "vehicle_steering_right_hand_side"),
                    getBoolean(row, "vehicle_steering_right_side_originally"),
                    getBoolean(row, "vehicle_catalyst_absorber"),
                    getString(row, "vehicle_manufacturer_name"),
                    getString(row, "vehicle_car_transport_institute_code"),
                    getDouble(row, "vehicle_wheel_base_steering_other_axles"),
                    getDouble(row, "vehicle_maximum_track_width"),
                    getDouble(row, "vehicle_avg_spacing_col"),
                    getBoolean(row, "vehicle_min_track_width"),
                    getDouble(row, "vehicle_exhaust_emission_reduction"),
                    getString(row, "vehicle_type_encoding_kind_subtype_destiny"),
                    getString(row, "vehicle_code_kind_subgenus_destiny"),
                    getString(row, "vehicle_date_of_deregistration"),
                    getString(row, "vehicle_reason_for_deregistration_of_vehicle"),
                    getString(row, "vehicle_date_data_input"),
                    getString(row, "vehicle_registration_voivodeship"),
                    getString(row, "vehicle_registration_community"),
                    getString(row, "vehicle_registration_country"),
                    getString(row, "vehicle_owner_voivodeship"),
                    getString(row, "vehicle_owner_district"),
                    getString(row, "vehicle_owner_commune"),
                    getString(row, "vehicle_owner_voivodeship_code"),
                    getString(row, "vehicle_voivodeship_code"),
                    getDouble(row, "vehicle_co2_emission_level_first_fuel_alternative")
            ));
        }
        return vehicles;
    }

    List<VehiclesParams> findVehicleParams(VehiclesParamsFilter vehiclesParamsFilter) {
        List<VehiclesParams> vehiclesParams = new ArrayList<>();
        String query = "SELECT * FROM vin_vehicles_params";
        if (vehiclesParamsFilter != null) {
            query += " WHERE 1=1";
            if (vehiclesParamsFilter.getId() != null) {
                query += " AND id = " + vehiclesParamsFilter.getId();
            }
            if (vehiclesParamsFilter.getLastRefreshDateTime() != null) {
                query += " AND last_refresh_date_time = " + vehiclesParamsFilter.getLastRefreshDateTime();
            }
            if (vehiclesParamsFilter.getVoivodeshipCode() != null) {
                query += " AND voivodeship_code = '" + vehiclesParamsFilter.getVoivodeshipCode() + "'";
            }
            if (vehiclesParamsFilter.getDateFrom() != null) {
                query += " AND date_from = '" + vehiclesParamsFilter.getDateFrom() + "'";
            }
            if (vehiclesParamsFilter.getDateTo() != null) {
                query += " AND date_to = '" + vehiclesParamsFilter.getDateTo() + "'";
            }
            if (vehiclesParamsFilter.getPage() != null) {
                query += " AND page = '" + vehiclesParamsFilter.getPage() + "'";
            }
            if (vehiclesParamsFilter.getPage_check() != null) {
                query += " AND generating_params_date_time = '" + vehiclesParamsFilter.getPage_check() + "'";
            }
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> row : rows) {
            vehiclesParams.add(new VehiclesParams(
                    getLong(row, "id"),
                    getString(row, "voivodeship_code"),
                    getDateTime(row, "last_refresh_date_time"),
                    getDateTime(row, "date_from").toLocalDate(),
                    getDateTime(row, "date_to").toLocalDate(),
                    getInteger(row, "page"),
                    getDateTime(row, "generating_params_date_time")
            ));
        }
        return vehiclesParams;
    }

    Long createVehicles(UpdateVehicles updateVehicles) {

        String query = "INSERT INTO vin_vehicles (last_refresh_date_time, vech_id, vehicles_vin, vehicles_registration_community, vehicles_registration_voivodeship, vehicles_registration_country, vehicles_date_last_registration_in_country, vehicles_date_first_registration, vehicles_date_first_registration_in_country, vehicles_date_first_registration_abroad, vehicles_date_data_input, vehicles_date_of_deregistration, vehicles_allowed_package, vehicles_permissible_gross_weight, vehicles_permissible_total_weight_of_vehicle_combinations, vehicles_co2_emission_level, vehicles_co2_emission_level_first_fuel_alternative, vehicles_co2_emission_level_second_fuel_alternative, vehicles_category, vehicles_car_transport_institute_code, vehicles_code_kind_subgenus_destiny, vehicles_number_seats_total, vehicles_number_of_seats, vehicles_number_of_standing_places, vehicles_number_of_axles, vehicles_maximum_payload, vehicles_maximum_total_mass, vehicles_max_total_weight_of_trailed_trailer_without_brake, vehicles_max_total_weight_trailer_without_brake, vehicles_max_net_power_of_engine_hybrid, vehicles_maximum_track_width, vehicles_engine_net_power, vehicles_brand, vehicles_mass_of_ready_to_run, vehicles_own_mass, vehicles_min_track_width, vehicles_model, vehicles_allowable_axle_load, vehicles_maximum_axle_weight, vehicles_manufacturer_name, vehicles_origin, vehicles_subtype, vehicles_engine_displacement, vehicles_destination, vehicles_reason_for_deregistration_of_vehicle, vehicles_exhaust_emission_reduction, vehicles_kind_of_second_alternative_fuel, vehicles_type_encoding_kind_subtype_destiny, vehicles_type_of_fuel, vehicles_kind_of_first_alternative_fuel, vehicles_kind, vehicles_nameplate_type, vehicles_kind_of_suspensions, vehicles_year_of_production, vehicles_wheel_base_steering_other_axles, vehicles_owner_commune, vehicles_owner_district, vehicles_owner_voivodeship, vehicles_method_of_production, vehicles_avg_spacing_col, vehicles_avg_fuel_consumption, vehicles_motor_power_to_weight_ratio_motorcycles, vehicles_type, vehicles_variant, vehicles_version, vehicles_equipment_and_type_of_radar_device, vehicles_steering_right_hand_side, vehicles_steering_right_side_originally, vehicles_hook, vehicles_catalyst_absorber, vehicles_voivodeship_code, vehicles_owner_voivodeship_code)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(query,
                null,
                updateVehicles.getCepikId(),
                updateVehicles.getVin(),
                updateVehicles.getRegistrationCommune(),
                updateVehicles.getRegistrationVoivodship(),
                updateVehicles.getRegistrationDistrict(),
                updateVehicles.getLastRegistrationDateInCountry(),
                updateVehicles.getDateOfFirstRegistration(),
                updateVehicles.getInitialRegistrationDateInCountry(),
                updateVehicles.getRegistrationDateAbroad(),
                updateVehicles.getDataInputDate(),
                updateVehicles.getVehicleDeregistrationDate(),
                updateVehicles.getPermissiblePackage(),
                updateVehicles.getPermissibleGrossWeight(),
                updateVehicles.getPermissibleTotalMassOfVehiclesSet(),
                updateVehicles.getCo2EmissionLevel(),
                updateVehicles.getCo2EmissionsAlternativeFuel1(),
                updateVehicles.getCo2EmissionsAlternativeFuel1(),
                updateVehicles.getCarCategory(),
                updateVehicles.getCarTransportInstituteCode(),
                updateVehicles.getCodeKindSubgenusDestiny(),
                updateVehicles.getPlacesNumberInTotal(),
                updateVehicles.getSeatsNumber(),
                updateVehicles.getStandingPlacesNumber(),
                updateVehicles.getAxlesNumber(),
                updateVehicles.getMaxPackage(),
                updateVehicles.getMaxTotalMass(),
                updateVehicles.getMaxTotalWeightTrailerWithoutBrake(),
                updateVehicles.getMaxTotalWeightTrailerWithBrake(),
                updateVehicles.getHybridEngineNetPower(),
                updateVehicles.getMaxTrackWheel(),
                updateVehicles.getEngineNetPower(),
                updateVehicles.getBrand(),
                updateVehicles.getMassOfVehicleReadyToRun(),
                updateVehicles.getOwnMass(),
                updateVehicles.getMinTrackWheel(),
                updateVehicles.getModel(),
                updateVehicles.getPermissibleAxleLoad(),
                updateVehicles.getMaxTrackWheel(),
                updateVehicles.getManufacturerName(),
                updateVehicles.getCarOrigin(),
                updateVehicles.getCarSubKind(),
                updateVehicles.getEngineDisplacement(),
                updateVehicles.getCarPurpose(),
                updateVehicles.getVehicleDeregistrationReason(),
                updateVehicles.getExhaustEmissionReduction(),
                updateVehicles.getSecondAlternativefuelType(),
                updateVehicles.getEncodingTypeSubtypeDestiny(),
                updateVehicles.getFuelType(),
                updateVehicles.getFirstAlternativefuelType(),
                updateVehicles.getCarKind(),
                updateVehicles.getCarNamePlateKind(),
                updateVehicles.getSuspensionType(),
                updateVehicles.getCarProductionYear(),
                updateVehicles.getWheelBaseSteeringOtherAxles(),
                updateVehicles.getOwnerCommune(),
                updateVehicles.getOwnerDistrict(),
                updateVehicles.getOwnerVoivodship(),
                updateVehicles.getCarProductionMethod(),
                updateVehicles.getAvgTrackWheel(),
                updateVehicles.getAverageFuelConsumption(),
                updateVehicles.getMotorPowerToUnladenWeightRatioMotorcycle(),
                updateVehicles.getType(),
                updateVehicles.getVariant(),
                updateVehicles.getVersion(),
                updateVehicles.getEquipmentAndTypeOfRadarDevice(),
                updateVehicles.getSteeringRightHandSide(),
                updateVehicles.getSteeringRightHandSideOrigin(),
                updateVehicles.getHook(),
                updateVehicles.getCatalystAbsorber(),
                updateVehicles.getVoivodshipCode(),
                updateVehicles.getOwnerVoivodshipCode()
        );
        return commonJdbcRepository.getLastInsertedId();
    }

    void updateVehicles(UpdateVehicles updateVehicles) {
        String query = "UPDATE vin_vehicles SET " +
                "last_refresh_date_time = ?, " +
                "vehicles_registration_community = ?, " +
                "vehicles_registration_voivodeship = ?, " +
                "vehicles_date_last_registration_in_country = ?, " +
                "vehicles_date_first_registration = ?, " +
                "vehicles_date_first_registration_in_country = ?, " +
                "vehicles_date_first_registration_abroad = ?, " +
                "vehicles_date_data_input = ?, " +
                "vehicles_date_of_deregistration = ?, " +
                "vehicles_allowed_package = ?, " +
                "vehicles_permissible_gross_weight = ?, " +
                "vehicles_permissible_total_weight_of_vehicle_combinations = ?, " +
                "vehicles_co2_emission_level = ?, " +
                "vehicles_co2_emission_level_first_fuel_alternative = ?, " +
                "vehicles_co2_emission_level_second_fuel_alternative = ?, " +
                "vehicles_category = ?, " +
                "vehicles_car_transport_institute_code = ?, " +
                "vehicles_code_kind_subgenus_destiny = ?, " +
                "vehicles_number_seats_total = ?, " +
                "vehicles_number_of_seats = ?, " +
                "vehicles_number_of_standing_places = ?, " +
                "vehicles_number_of_axles = ?, " +
                "vehicles_maximum_payload = ?, " +
                "vehicles_maximum_total_mass = ?, " +
                "vehicles_max_total_weight_of_trailed_trailer_without_brake = ?, " +
                "vehicles_max_total_weight_trailer_without_brake = ?, " +
                "vehicles_max_net_power_of_engine_hybrid = ?, " +
                "vehicles_maximum_track_width = ?, " +
                "vehicles_engine_net_power = ?, " +
                "vehicles_brand = ?, " +
                "vehicles_mass_of_ready_to_run = ?, " +
                "vehicles_own_mass = ?, " +
                "vehicles_min_track_width = ?, " +
                "vehicles_model = ?, " +
                "vehicles_allowable_axle_load = ?, " +
                "vehicles_maximum_axle_weight = ?, " +
                "vehicles_manufacturer_name = ?, " +
                "vehicles_origin = ?, " +
                "vehicles_subtype = ?, " +
                "vehicles_engine_displacement = ?, " +
                "vehicles_destination = ?, " +
                "vehicles_reason_for_deregistration_of_vehicle = ?, " +
                "vehicles_exhaust_emission_reduction = ?, " +
                "vehicles_kind_of_second_alternative_fuel = ?, " +
                "vehicles_type_encoding_kind_subtype_destiny = ?, " +
                "vehicles_type_of_fuel = ?, " +
                "vehicles_kind_of_first_alternative_fuel = ?, " +
                "vehicles_kind = ?, " +
                "vehicles_nameplate_type = ?, " +
                "vehicles_kind_of_suspensions = ?, " +
                "vehicles_year_of_production = ?, " +
                "vehicles_wheel_base_steering_other_axles = ?, " +
                "vehicles_owner_commune = ?, " +
                "vehicles_owner_district = ?, " +
                "vehicles_owner_voivodeship = ?, " +
                "vehicles_method_of_production = ?, " +
                "vehicles_avg_spacing_col = ?, " +
                "vehicles_avg_fuel_consumption = ?, " +
                "vehicles_motor_power_to_weight_ratio_motorcycles = ?, " +
                "vehicles_type = ?, " +
                "vehicles_variant = ?, " +
                "vehicles_version = ?, " +
                "vehicles_equipment_and_type_of_radar_device = ?, " +
                "vehicles_steering_right_hand_side = ?, " +
                "vehicles_steering_right_side_originally = ?, " +
                "vehicles_hook = ?, " +
                "vehicles_catalyst_absorber = ?, " +
                "vehicles_voivodeship_code = ?, " +
                "vehicles_owner_voivodeship_code = ? " +
                "WHERE vech_id = ?;";

        jdbcTemplate.update(query,
                LocalDateTime.now(),
                updateVehicles.getRegistrationCommune(),
                updateVehicles.getRegistrationVoivodship(),
                updateVehicles.getLastRegistrationDateInCountry(),
                updateVehicles.getDateOfFirstRegistration(),
                updateVehicles.getInitialRegistrationDateInCountry(),
                updateVehicles.getRegistrationDateAbroad(),
                updateVehicles.getDataInputDate(),
                updateVehicles.getVehicleDeregistrationDate(),
                updateVehicles.getPermissiblePackage(),
                updateVehicles.getPermissibleGrossWeight(),
                updateVehicles.getPermissibleTotalMassOfVehiclesSet(),
                updateVehicles.getCo2EmissionLevel(),
                updateVehicles.getCo2EmissionsAlternativeFuel1(),
                updateVehicles.getCo2EmissionsAlternativeFuel1(),
                updateVehicles.getCarCategory(),
                updateVehicles.getCarTransportInstituteCode(),
                updateVehicles.getCodeKindSubgenusDestiny(),
                updateVehicles.getPlacesNumberInTotal(),
                updateVehicles.getSeatsNumber(),
                updateVehicles.getStandingPlacesNumber(),
                updateVehicles.getAxlesNumber(),
                updateVehicles.getMaxPackage(),
                updateVehicles.getMaxTotalMass(),
                updateVehicles.getMaxTotalWeightTrailerWithoutBrake(),
                updateVehicles.getMaxTotalWeightTrailerWithBrake(),
                updateVehicles.getHybridEngineNetPower(),
                updateVehicles.getMaxTrackWheel(),
                updateVehicles.getEngineNetPower(),
                updateVehicles.getBrand(),
                updateVehicles.getMassOfVehicleReadyToRun(),
                updateVehicles.getOwnMass(),
                updateVehicles.getMinTrackWheel(),
                updateVehicles.getModel(),
                updateVehicles.getPermissibleAxleLoad(),
                updateVehicles.getMaxTrackWheel(), //cccc
                updateVehicles.getManufacturerName(),
                updateVehicles.getCarOrigin(),
                updateVehicles.getCarSubKind(),
                updateVehicles.getEngineDisplacement(),
                updateVehicles.getCarPurpose(),
                updateVehicles.getVehicleDeregistrationReason(),
                updateVehicles.getExhaustEmissionReduction(),
                updateVehicles.getSecondAlternativefuelType(),
                updateVehicles.getEncodingTypeSubtypeDestiny(),
                updateVehicles.getFuelType(),
                updateVehicles.getFirstAlternativefuelType(),
                updateVehicles.getCarKind(),
                updateVehicles.getCarNamePlateKind(),
                updateVehicles.getSuspensionType(),
                updateVehicles.getCarProductionYear(),
                updateVehicles.getWheelBaseSteeringOtherAxles(),
                updateVehicles.getOwnerCommune(),
                updateVehicles.getOwnerDistrict(),
                updateVehicles.getOwnerVoivodship(),
                updateVehicles.getCarProductionMethod(),
                updateVehicles.getAvgTrackWheel(),
                updateVehicles.getAverageFuelConsumption(),
                updateVehicles.getMotorPowerToUnladenWeightRatioMotorcycle(),
                updateVehicles.getType(),
                updateVehicles.getVariant(),
                updateVehicles.getVersion(),
                updateVehicles.getEquipmentAndTypeOfRadarDevice(),
                updateVehicles.getSteeringRightHandSide(),
                updateVehicles.getSteeringRightHandSideOrigin(),
                updateVehicles.getHook(),
                updateVehicles.getCatalystAbsorber(),
                updateVehicles.getVoivodshipCode(),
                updateVehicles.getOwnerVoivodshipCode(),
                updateVehicles.getCepikId()
        );

    }

    void updateVehicle(UpdateVehicle updateVehicle) {
        String query = "UPDATE vin_vehicles SET " +
                "last_refresh_date_time = ?, " +
                "vehicle_registration_community = ?, " +
                "vehicle_registration_voivodeship = ?, " +
                "vehicle_date_last_registration_in_country = ?, " +
                "vehicle_date_first_registration = ?, " +
                "vehicle_date_first_registration_in_country = ?, " +
                "vehicle_date_first_registration_abroad = ?, " +
                "vehicle_date_data_input = ?, " +
                "vehicle_date_of_deregistration = ?, " +
                "vehicle_allowed_package = ?, " +
                "vehicle_permissible_gross_weight = ?, " +
                "vehicle_permissible_total_weight_of_vehicle_combinations = ?, " +
                "vehicle_co2_emission_level = ?, " +
                "vehicle_co2_emission_level_first_fuel_alternative = ?, " +
                "vehicle_co2_emission_level_second_fuel_alternative = ?, " +
                "vehicle_category = ?, " +
                "vehicle_car_transport_institute_code = ?, " +
                "vehicle_code_kind_subgenus_destiny = ?, " +
                "vehicle_number_seats_total = ?, " +
                "vehicle_number_of_seats = ?, " +
                "vehicle_number_of_standing_places = ?, " +
                "vehicle_number_of_axles = ?, " +
                "vehicle_maximum_payload = ?, " +
                "vehicle_maximum_total_mass = ?, " +
                "vehicle_max_total_weight_of_trailed_trailer_without_brake = ?, " +
                "vehicle_max_total_weight_trailer_without_brake = ?, " +
                "vehicle_max_net_power_of_engine_hybrid = ?, " +
                "vehicle_maximum_track_width = ?, " +
                "vehicle_engine_net_power = ?, " +
                "vehicle_brand = ?, " +
                "vehicle_mass_of_ready_to_run = ?, " +
                "vehicle_own_mass = ?, " +
                "vehicle_min_track_width = ?, " +
                "vehicle_model = ?, " +
                "vehicle_allowable_axle_load = ?, " +
                "vehicle_maximum_axle_weight = ?, " +
                "vehicle_manufacturer_name = ?, " +
                "vehicle_origin = ?, " +
                "vehicle_subtype = ?, " +
                "vehicle_engine_displacement = ?, " +
                "vehicle_destination = ?, " +
                "vehicle_reason_for_deregistration_of_vehicle = ?, " +
                "vehicle_exhaust_emission_reduction = ?, " +
                "vehicle_kind_of_second_alternative_fuel = ?, " +
                "vehicle_type_encoding_kind_subtype_destiny = ?, " +
                "vehicle_type_of_fuel = ?, " +
                "vehicle_kind_of_first_alternative_fuel = ?, " +
                "vehicle_kind = ?, " +
                "vehicle_nameplate_type = ?, " +
                "vehicle_kind_of_suspensions = ?, " +
                "vehicle_year_of_production = ?, " +
                "vehicle_wheel_base_steering_other_axles = ?, " +
                "vehicle_owner_commune = ?, " +
                "vehicle_owner_district = ?, " +
                "vehicle_owner_voivodeship = ?, " +
                "vehicle_method_of_production = ?, " +
                "vehicle_avg_spacing_col = ?, " +
                "vehicle_avg_fuel_consumption = ?, " +
                "vehicle_motor_power_to_weight_ratio_motorcycles = ?, " +
                "vehicle_type = ?, " +
                "vehicle_variant = ?, " +
                "vehicle_version = ?, " +
                "vehicle_equipment_and_type_of_radar_device = ?, " +
                "vehicle_steering_right_hand_side = ?, " +
                "vehicle_steering_right_side_originally = ?, " +
                "vehicle_hook = ?, " +
                "vehicle_catalyst_absorber = ?, " +
                "vehicle_voivodeship_code = ?, " +
                "vehicle_owner_voivodeship_code = ? " +
                "WHERE vech_id = ?;";

        jdbcTemplate.update(query,
                LocalDateTime.now(),
                updateVehicle.getRegistrationCommune(),
                updateVehicle.getRegistrationVoivodship(),
                updateVehicle.getLastRegistrationDateInCountry(),
                updateVehicle.getDateOfFirstRegistration(),
                updateVehicle.getInitialRegistrationDateInCountry(),
                updateVehicle.getRegistrationDateAbroad(),
                updateVehicle.getDataInputDate(),
                updateVehicle.getVehicleDeregistrationDate(),
                updateVehicle.getPermissiblePackage(),
                updateVehicle.getPermissibleGrossWeight(),
                updateVehicle.getPermissibleTotalMassOfVehiclesSet(),
                updateVehicle.getCo2EmissionLevel(),
                updateVehicle.getCo2EmissionsAlternativeFuel1(),
                updateVehicle.getCo2EmissionsAlternativeFuel1(),
                updateVehicle.getCarCategory(),
                updateVehicle.getCarTransportInstituteCode(),
                updateVehicle.getCodeKindSubgenusDestiny(),
                updateVehicle.getPlacesNumberInTotal(),
                updateVehicle.getSeatsNumber(),
                updateVehicle.getStandingPlacesNumber(),
                updateVehicle.getAxlesNumber(),
                updateVehicle.getMaxPackage(),
                updateVehicle.getMaxTotalMass(),
                updateVehicle.getMaxTotalWeightTrailerWithoutBrake(),
                updateVehicle.getMaxTotalWeightTrailerWithBrake(),
                updateVehicle.getHybridEngineNetPower(),
                updateVehicle.getMaxTrackWheel(),
                updateVehicle.getEngineNetPower(),
                updateVehicle.getBrand(),
                updateVehicle.getMassOfVehicleReadyToRun(),
                updateVehicle.getOwnMass(),
                updateVehicle.getMinTrackWheel(),
                updateVehicle.getModel(),
                updateVehicle.getPermissibleAxleLoad(),
                updateVehicle.getMaxTrackWheel(), //cccc
                updateVehicle.getManufacturerName(),
                updateVehicle.getCarOrigin(),
                updateVehicle.getCarSubKind(),
                updateVehicle.getEngineDisplacement(),
                updateVehicle.getCarPurpose(),
                updateVehicle.getVehicleDeregistrationReason(),
                updateVehicle.getExhaustEmissionReduction(),
                updateVehicle.getSecondAlternativefuelType(),
                updateVehicle.getEncodingTypeSubtypeDestiny(),
                updateVehicle.getFuelType(),
                updateVehicle.getFirstAlternativefuelType(),
                updateVehicle.getCarKind(),
                updateVehicle.getCarNamePlateKind(),
                updateVehicle.getSuspensionType(),
                updateVehicle.getCarProductionYear(),
                updateVehicle.getWheelBaseSteeringOtherAxles(),
                updateVehicle.getOwnerCommune(),
                updateVehicle.getOwnerDistrict(),
                updateVehicle.getOwnerVoivodship(),
                updateVehicle.getCarProductionMethod(),
                updateVehicle.getAvgTrackWheel(),
                updateVehicle.getAverageFuelConsumption(),
                updateVehicle.getMotorPowerToUnladenWeightRatioMotorcycle(),
                updateVehicle.getType(),
                updateVehicle.getVariant(),
                updateVehicle.getVersion(),
                updateVehicle.getEquipmentAndTypeOfRadarDevice(),
                updateVehicle.getSteeringRightHandSide(),
                updateVehicle.getSteeringRightHandSideOrigin(),
                updateVehicle.getHook(),
                updateVehicle.getCatalystAbsorber(),
                updateVehicle.getVoivodshipCode(),
                updateVehicle.getOwnerVoivodshipCode(),
                updateVehicle.getCepikId()
        );
    }
}