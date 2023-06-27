package pl.com.mike.developer.web;

import pl.com.mike.developer.PremiseGetResponse;
import pl.com.mike.developer.PremisesGetResponse;
import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.ArrayList;
import java.util.List;

public class ConverterToResponse {

    public static PremiseGetResponse premiseDataToResponse(PremiseData premiseData) {
        return new PremiseGetResponse(
                premiseData.getId(),
                premiseData.getType(),
                premiseData.getNumber(),
                premiseData.getFloor(),
                premiseData.getSurfacePerSqMeter(),
                premiseData.getPricePerSqMeter(),
                premiseData.getTotalPrice(),
                premiseData.getNumberOfRooms(),
                premiseData.getTechnicalStatus(),
                premiseData.getSalesStatus(),
                premiseData.getExposure(),
                premiseData.getBalcony(),
                premiseData.getGarden(),
                premiseData.getTerrace(),
                premiseData.getLoggia(),
                premiseData.getBuildingId()
        );
    }

    public static PremisesGetResponse premisesDataToResponse(List<PremiseData> premisesData) {
        List<PremiseGetResponse> premisesGetResponse = new ArrayList<>();
        for (PremiseData list : premisesData) {
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
        return new PremisesGetResponse(premisesGetResponse);
    }

//    public static List<PremiseGetResponse> premiseDataToResponse(List<PremiseData> premiseData) {
//        List<PremiseGetResponse> premisesGetResponse = new ArrayList<>();
//        for (PremiseData list : premiseData) {
//            premisesGetResponse.add(
//                    new PremiseGetResponse(
//                            list.getId(),
//                            list.getType(),
//                            list.getNumber(),
//                            list.getFloor(),
//                            list.getSurfacePerSqMeter(),
//                            list.getPricePerSqMeter(),
//                            list.getTotalPrice(),
//                            list.getNumberOfRooms(),
//                            list.getTechnicalStatus(),
//                            list.getSalesStatus(),
//                            list.getExposure(),
//                            list.getBalcony(),
//                            list.getGarden(),
//                            list.getTerrace(),
//                            list.getLoggia(),
//                            list.getBuildingId()
//                    )
//            );
//        }
//        return premisesGetResponse;
//    }


}
