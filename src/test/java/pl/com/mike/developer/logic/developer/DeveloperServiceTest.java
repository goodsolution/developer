package pl.com.mike.developer.logic.developer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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