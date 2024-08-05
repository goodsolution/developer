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
        premiseData.setId(1L);
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

        Long expectedId = 1L;

        // Mock dependencies
        PremiseService premiseService = mock(PremiseService.class);
        when(premiseService.createPremiseData(premiseData)).thenReturn(expectedId);

        // Create the endpoint instance with the mocked service
        PremiseEndpoint premiseEndpoint = new PremiseEndpoint(premiseService);

        // Call the method under test
        ResponseEntity<Long> responseEntity = premiseEndpoint.createPremise(premiseData);

        // Assertions
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(expectedId);  // Direct comparison without recursive comparison

        // Verify interactions
        verify(premiseService, times(1)).createPremiseData(premiseData);
    }



}
