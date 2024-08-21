package pl.com.mike.developer.elearning.service;

import org.junit.jupiter.api.Test;
import pl.com.mike.developer.elearning.TaskDto;

import static org.junit.jupiter.api.Assertions.*;

class TaskParserTest {

    private final TaskParser taskParser = new TaskParser();

    @Test
    void parseContent_shouldReturnTaskDto_whenInputIsValid() {
        // Given
        String commandText = "#title.pl(){\"Użycie konstruktora\"};\n" +
                "#title.en(){\"Constructor usage\"};\n" +
                "#description.pl(){\"opis opis opis\"};\n" +
                "#description.en(){\"opis opis opis\"};";

        // When
        TaskDto result = taskParser.parseContent(commandText);

        // Then
        assertNotNull(result);
        assertEquals("Użycie konstruktora", result.getTitlePl());
        assertEquals("Constructor usage", result.getTitleEn());
        assertEquals("opis opis opis", result.getDescriptionPl());
        assertEquals("opis opis opis", result.getDescriptionEn());
    }

    @Test
    void parseContent_shouldThrowIllegalArgumentException_whenInputIsInvalid() {
        // Given
        String invalidCommandText = "#title.pl(){\"Invalid format\"};";

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> taskParser.parseContent(invalidCommandText));
    }

    @Test
    void parseContent_shouldThrowIllegalArgumentException_whenInputIsEmpty() {
        // Given
        String emptyCommandText = "";

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> taskParser.parseContent(emptyCommandText));
    }

    @Test
    void parseContent_shouldThrowIllegalArgumentException_whenInputIsNull() {
        // Given
        String nullCommandText = null;

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> taskParser.parseContent(nullCommandText));
    }

}