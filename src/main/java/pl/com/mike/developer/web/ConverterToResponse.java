package pl.com.mike.developer.web;

import pl.com.mike.developer.*;
import pl.com.mike.developer.domain.developer.*;

import java.util.ArrayList;
import java.util.List;

public class ConverterToResponse {

    public static List<DeveloperGetResponse> developerDataToResponse(List<DeveloperData> developerData) {
        List<DeveloperGetResponse> developersGetResponse = new ArrayList<>();
        for (DeveloperData data : developerData) {
            developersGetResponse.add(
                    new DeveloperGetResponse(
                            data.getId(),
                            data.getName(),
                            data.getAddressCountry(),
                            data.getAddressStreet(),
                            data.getAddressBuildingNumber(),
                            data.getAddressFlatNumber(),
                            data.getAddressPostalCode(),
                            data.getTelephoneNumber(),
                            data.getFaxNumber(),
                            data.getEmail(),
                            data.getTaxIdentificationNumber(),
                            data.getCityData().getId(),
                            data.getVoivodeshipData().getId()
                    )
            );
        }
        return developersGetResponse;
    }


}
