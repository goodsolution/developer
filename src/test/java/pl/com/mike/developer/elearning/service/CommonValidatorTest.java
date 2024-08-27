package pl.com.mike.developer.elearning.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonValidatorTest {

    CommonValidator validator = new CommonValidator();

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
    void testOneLineInvalidInputNoHash() {
        //Given
        String validText = "title.pl(){\"Użycie konstruktora\"};\n";
        //When
        Result result = validator.validate(validText);
        //Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testValidInputTitlePlDescriptionPlOnly() {
        //Given
        String validText = "#title.pl(){\"Użycie konstruktora\"};\n" +
                "#description.pl(){\"opis opis opis\"};";

        // Debug: Ensure input is correct
        System.out.println("Input being validated: \n" + validText);

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
    void testInValidInputWithOneLineWithoutBrackets() {
        // Given
        String validText = "#title.pl{\"Użycie konstruktora\"};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutLeftBracket() {
        // Given
        String validText = "#title.pl){\"Użycie konstruktora\"};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutRightBracket() {
        // Given
        String validText = "#title.pl({\"Użycie konstruktora\"};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutCurlyBraces() {
        // Given
        String validText = "#title.pl()\"Użycie konstruktora\";";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutQuotationMark() {
        // Given
        String validText = "#title.pl(){Użycie konstruktora};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutLeftQuotationMark() {
        // Given
        String validText = "#title.pl(){Użycie konstruktora\"};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
    }

    @Test
    void testInValidInputWithOneLineWithoutRightQuotationMark() {
        // Given
        String validText = "#title.pl(){\"Użycie konstruktora};";

        // When
        Result result = validator.validate(validText);

        // Then
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(Reason.UNKNOWN, result.getReason());
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