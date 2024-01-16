package pl.com.mike.developer.web;

import pl.com.mike.developer.DeveloperGetResponse;
import pl.com.mike.developer.PremiseGetResponse;
import pl.com.mike.developer.domain.developer.DeveloperData;
import pl.com.mike.developer.domain.developer.PremiseData;
import java.util.ArrayList;
import java.util.List;

public class ConverterToResponse {

    public static List<PremiseGetResponse> premisesDataToResponse(List<PremiseData> premises){
        List<PremiseGetResponse> premisesGetResponse = new ArrayList<>();
        for (PremiseData list : premises) {
            premisesGetResponse.add(
                    new PremiseGetResponse(
                            list.getId(),
                            list.getType(),
                            list.getNumber(),
                            list.getFloor(),
                            list.getSurfacePerSqMeter(),
                            list.getPricePerSqMeter(),
                            list.getTotalPrice(),
                            list.getNumberOfRooms(),
                            list.getTechnicalStatus(),
                            list.getSalesStatus(),
                            list.getExposure(),
                            list.getBalcony(),
                            list.getGarden(),
                            list.getTerrace(),
                            list.getLoggia(),
                            list.getBuildingId()
                    )
            );
        }
        return premisesGetResponse;
    }

    public static List<DeveloperGetResponse> developersDataToResponse(List<DeveloperData> developers) {
        List<DeveloperGetResponse> developersGetResponse = new ArrayList<>();
        for (DeveloperData developer : developers) {
            developersGetResponse.add(
                    new DeveloperGetResponse(
                            developer.getId(),
                            developer.getName(),
                            developer.getAddressCountry(),
                            developer.getAddressStreet(),
                            developer.getAddressBuildingNumber(),
                            developer.getAddressFlatNumber(),
                            developer.getAddressPostalCode(),
                            developer.getTelephoneNumber(),
                            developer.getFaxNumber(),
                            developer.getEmail(),
                            developer.getTaxIdentificationNumber(),
                            developer.getCityId(),
                            developer.getVoivodeshipId(),
                            developer.getLogoUrl(),
                            developer.getCode()
                    )
            );
        }
        return developersGetResponse;
    }


    public static List<DeveloperGetResponse> developerDataToResponse(DeveloperData developer) {
        List<DeveloperGetResponse> developersGetResponse = new ArrayList<>();
             developersGetResponse.add(
                    new DeveloperGetResponse(
                            developer.getId(),
                            developer.getName(),
                            developer.getAddressCountry(),
                            developer.getAddressStreet(),
                            developer.getAddressBuildingNumber(),
                            developer.getAddressFlatNumber(),
                            developer.getAddressPostalCode(),
                            developer.getTelephoneNumber(),
                            developer.getFaxNumber(),
                            developer.getEmail(),
                            developer.getTaxIdentificationNumber(),
                            developer.getCityId(),
                            developer.getVoivodeshipId(),
                            developer.getLogoUrl(),
                            developer.getCode()
                    )
            );
        return developersGetResponse;
    }


}
