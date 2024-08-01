package pl.com.mike.developer;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.com.mike.developer.domain.developer.PremiseData;
import pl.com.mike.developer.logic.developer.PremiseService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PremiseEndpointTest {

    @Test
    void createPremise() {
        // Prepare test data
        PremiseData premiseData = new PremiseData();
        premiseData.setType("type");
        premiseData.setNumber(1);
        premiseData.setFloor(2);
        premiseData.setSurfacePerSqMeter(3.0);
        premiseData.setPricePerSqMeter(4.0);
        premiseData.setTotalPrice(BigDecimal.valueOf(6.0));
        premiseData.setNumberOfRooms(5);
        premiseData.setTechnicalStatus("technicalStatus");
        premiseData.setSalesStatus("salesStatus");
        premiseData.setExposure("exposure");
        premiseData.setBalcony(true);
        premiseData.setGarden(true);
        premiseData.setTerrace(true);
        premiseData.setLoggia(true);

        PremisePostResponse premisePostResponse = new PremisePostResponse(premiseData);

        // Mock dependencies
        PremiseService premiseService = mock(PremiseService.class);
        when(premiseService.createPremiseData(premiseData)).thenReturn(premiseData);

        // Create the endpoint instance with the mocked service
        PremiseEndpoint premiseEndpoint = new PremiseEndpoint(premiseService);

        // Call the method under test
        ResponseEntity<PremisePostResponse> responseEntity = premiseEndpoint.createPremise(premiseData);

        // Assertions
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).usingRecursiveComparison().isEqualTo(premisePostResponse);

        // Verify interactions (optional)
        // For example, you can verify if the service method was called exactly once with the expected data
         verify(premiseService, times(1)).createPremiseData(premiseData);
    }

}
