package pl.com.mike.developer.web;

import pl.com.mike.developer.BuildingGetResponse;
import pl.com.mike.developer.DeveloperGetResponse;
import pl.com.mike.developer.InvestmentGetResponse;
import pl.com.mike.developer.PremiseGetResponse;
import pl.com.mike.developer.domain.developer.BuildingData;
import pl.com.mike.developer.domain.developer.DeveloperData;
import pl.com.mike.developer.domain.developer.InvestmentData;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.ArrayList;
import java.util.List;

public class ConverterToResponse {

    public static List<PremiseGetResponse> premiseDataToResponse(List<PremiseData> premiseData) {
        List<PremiseGetResponse> premisesGetResponse = new ArrayList<>();
        for (PremiseData data : premiseData) {
            premisesGetResponse.add(
                    new PremiseGetResponse(
                            data.getId(),
                            data.getType(),
                            data.getNumber(),
                            data.getFloor(),
                            data.getSurfacePerSqMeter(),
                            data.getPricePerSqMeter(),
                            data.getTotalPrice(),
                            data.getNumberOfRooms(),
                            data.getTechnicalStatus(),
                            data.getSalesStatus(),
                            data.getExposure(),
                            data.getBalcony(),
                            data.getGarden(),
                            data.getTerrace(),
                            data.getLoggia(),
                            data.getBuildingData().getId()
                    )
            );
        }
        return premisesGetResponse;
    }

    public static List<InvestmentGetResponse> investmentDataToResponse(List<InvestmentData> investmentData) {
        List<InvestmentGetResponse> investmentsGetResponse = new ArrayList<>();
        for (InvestmentData data : investmentData) {
            investmentsGetResponse.add(
                    new InvestmentGetResponse(
                            data.getId(),
                            data.getName(),
                            data.getDescription(),
                            data.getAddressCountry(),
                            data.getAddressVoivodeship(),
                            data.getAddressCity(),
                            data.getAddressStreet(),
                            data.getDeveloperData().getId()
                    )
            );
        }
        return investmentsGetResponse;
    }

    public static List<BuildingGetResponse> buildingDataToResponse(List<BuildingData> buildingData) {
        List<BuildingGetResponse> buildingsGetResponse = new ArrayList<>();
        for (BuildingData data : buildingData) {
            buildingsGetResponse.add(
                    new BuildingGetResponse(
                            data.getId(),
                            data.getName(),
                            data.getAddressCountry(),
                            data.getAddressVoivodeship(),
                            data.getAddressCity(),
                            data.getAddressStreet(),
                            data.getAddressBuildingNumber(),
                            data.getAddressPostalCode(),
                            data.getInvestmentData().getId()
                    )
            );
        }
        return buildingsGetResponse;
    }

    public static List<DeveloperGetResponse> developerDataToResponse (List<DeveloperData> developerData) {
        List<DeveloperGetResponse> developersGetResponse = new ArrayList<>();
        for (DeveloperData data : developerData) {
            developersGetResponse.add(
                    new DeveloperGetResponse(
                            data.getId(),
                            data.getName(),
                            data.getAddressCountry(),
                            data.getAddressVoivodeship(),
                            data.getAddressCity(),
                            data.getAddressStreet(),
                            data.getAddressBuildingNumber(),
                            data.getAddressFlatNumber(),
                            data.getAddressPostalCode(),
                            data.getTelephoneNumber(),
                            data.getFaxNumber(),
                            data.getEmail(),
                            data.getTaxIdentificationNumber()
                    )
            );
        }
        return developersGetResponse;
    }

}
