package pl.com.mike.developer.elearning.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonValidatorTest {

    private final CommonValidator validator = new CommonValidator();

    @Test
    void testValidInput() {
        //Given
        String validText = "#title.pl(){\"Użycie konstruktora\"};\n" +
                "#title.en(){\"Constructor usage\"};\n" +
                "#description.pl(){\"opis opis opis\"};\n" +
                "#description.en(){\"opis opis opis\"};";
        //When
        Result result = validator.validate(validText);
        //Then
        Assertions.assertTrue(result.isValid());
        Assertions.assertEquals(Reason.OK, result.getReason());
    }

    @Test
    void testValidInputWithOneLine() {
        // Given
        String validText = "#title.pl(){\"Użycie konstruktora\"};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertTrue(result.isValid());
        Assertions.assertEquals(Reason.OK, result.getReason());
    }

    @Test
    void testValidInputWithSpaces() {
        //Given
        String validText = "#title.pl(){\"Użycie konstruktora\"};\n\n" +
                "#title.en(){\"Constructor usage\"};\n\n" +
                "#description.pl(){\"opis opis opis\"};\n\n" +
                "#description.en(){\"opis opis opis\"};";
        //When
        Result result = validator.validate(validText);
        //Then
        Assertions.assertTrue(result.isValid());
        Assertions.assertEquals(Reason.OK, result.getReason());
    }

    @Test
    void testInvalidInputWhenNoDescription() {
        //Given
        String invalidText = "#title.pl(){\"Użycie konstruktora\"};\n" +
                "#title.en(){\"Constructor usage\"};\n" +
                "#description.pl(){};\n" +
                "#description.en(){};";
        //When
        Result result = validator.validate(invalidText);
        //Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());

    }


    @Test
    void testInvalidInputWithUnknownCommand() {
        // Given
        String invalidText = "#unknown.pl(){\"Invalid command\"};\n" +
                "#title.en(){\"Constructor usage\"};";
        // When
        Result result = validator.validate(invalidText);
        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInvalidInputWithMultipleUnknownCommands() {
        // Given
        String invalidText = "#unknown.pl(){\"Invalid command\"};\n" +
                "#fake.en(){\"Another invalid command\"};";
        // When
        Result result = validator.validate(invalidText);
        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testEmptyInput() {
        //Given
        String emptyText = "";
        //When
        Result result = validator.validate(emptyText);
        //Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.EMPTY, result.getReason());
    }

}