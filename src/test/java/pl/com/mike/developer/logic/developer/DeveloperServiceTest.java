package pl.com.mike.developer.logic.developer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.com.mike.developer.domain.developer.City;
import pl.com.mike.developer.domain.developer.Developer;
import pl.com.mike.developer.domain.developer.DeveloperData;

import static org.mockito.Mockito.*;

class DeveloperServiceTest {

    @Mock
    DeveloperRepository developerRepository;

    @Mock
    CityService cityService;

    @InjectMocks
    DeveloperService developerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDeveloperById() {
    }

    @Test
    void getDeveloperByCode() {
    }

    @Test
    void createDeveloper() {
        // Prepare test data
        DeveloperData developerData = new DeveloperData();
        developerData.setName("Test Developer");
        developerData.setAddressCountry("Country");
        developerData.setAddressStreet("Street");
        developerData.setAddressBuildingNumber("123");
        developerData.setAddressFlatNumber("456");
        developerData.setAddressPostalCode("7890");
        developerData.setTelephoneNumber("123456789");
        developerData.setFaxNumber("987654321");
        developerData.setEmail("developer@example.com");
        developerData.setTaxIdentificationNumber("TAX123456");
        developerData.setCityId(1L);
        developerData.setLogoUrl("http://logo.url");
        developerData.setCode("DEV123");

        //Mock dependencies
        when(cityService.getCityById(1L)).thenReturn(new City());
        when(developerRepository.save(any(Developer.class))).thenAnswer(invocation -> {
            Developer developer = invocation.getArgument(0);
            developer.setId(1L);
            return developer;
        });

        // Call the method under test
        DeveloperData createdDeveloper = developerService.createDeveloper(developerData);

        // Assertions
        Assertions.assertNotNull(createdDeveloper);
        Assertions.assertEquals("Test Developer", createdDeveloper.getName());
        Assertions.assertEquals("Country", createdDeveloper.getAddressCountry());
        Assertions.assertEquals("Street", createdDeveloper.getAddressStreet());
        Assertions.assertEquals("123", createdDeveloper.getAddressBuildingNumber());
        Assertions.assertEquals("456", createdDeveloper.getAddressFlatNumber());
        Assertions.assertEquals("7890", createdDeveloper.getAddressPostalCode());
        Assertions.assertEquals("123456789", createdDeveloper.getTelephoneNumber());
        Assertions.assertEquals("987654321", createdDeveloper.getFaxNumber());
        Assertions.assertEquals("developer@example.com", createdDeveloper.getEmail());
        Assertions.assertEquals("TAX123456", createdDeveloper.getTaxIdentificationNumber());
        Assertions.assertEquals("http://logo.url", createdDeveloper.getLogoUrl());
        Assertions.assertEquals("DEV123", createdDeveloper.getCode());

        // Verify interactions
        verify(developerRepository, times(1)).save(any(Developer.class));

    }

    @Test
    void updateDeveloper() {
    }

    @Test
    void getAllActiveDevelopers() {
    }

    @Test
    void softDeleteDeveloper() {
    }
}