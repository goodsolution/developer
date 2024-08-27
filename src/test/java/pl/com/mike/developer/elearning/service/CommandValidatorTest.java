package pl.com.mike.developer.elearning.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CommandValidatorTest {

    @Mock
    private TitleValidator titleValidator;

    @Mock
    private DescriptionValidator descriptionValidator;

    @InjectMocks
    private CommandValidator commandValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateWithValidInput() {
        // Given
        String text = "#title.pl(){\"Użycie konstruktora\"};\n" +
                "#title.en(){\"Constructor usage\"};\n" +
                "#description.pl(){\"opis opis opis\"};\n" +
                "#description.en(){\"opis opis opis\"};";

        // When
        when(titleValidator.validate(anyString())).thenReturn(new Result(true, Reason.OK));
        when(descriptionValidator.validate(anyString())).thenReturn(new Result(true, Reason.OK));

        // Then
        Result result = commandValidator.validate(text);
        assertTrue(result.isValid(), "Validation should pass for correct input.");
        verify(titleValidator, times(2)).validate(anyString());
        verify(descriptionValidator, times(2)).validate(anyString());
    }

    @Test
    void testValidateWithEmptyInput() {
        //Given
        Result result = commandValidator.validate("");

        //Then
        assertFalse(result.isValid(), "Validation should fail for empty input.");
        assertEquals(Reason.UNKNOWN, result.getReason(), "Reason should be UNKNOWN for empty input.");
    }

    @Test
    void testValidateWithNullInput() {
        //Given
        Result result = commandValidator.validate(null);

        //Then
        assertFalse(result.isValid(), "Validation should fail for null input.");
        assertEquals(Reason.UNKNOWN, result.getReason(), "Reason should be UNKNOWN for null input.");
    }

}