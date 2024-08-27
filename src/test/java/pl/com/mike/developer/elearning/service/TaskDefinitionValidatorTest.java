package pl.com.mike.developer.elearning.service;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class TaskDefinitionValidatorTest {

    private TaskDefinitionValidator taskDefinitionValidator = new TaskDefinitionValidator();

    @Test
    void whenCorrectCommandsThenSuccess() {

        //given
        String description = "#description.pl(){\"opis opis opis\"};\n" +
                "#description.en(){\"opis opis opis\"};";

        //TODO add first - wszystkie możliwe komendy

        //when
        Result result = taskDefinitionValidator.validate(description);

        //then
        assertEquals(true, result.isValid());
        assertEquals(Reason.OK, result.getReason());


    }

    @Test
    void whenOnlyTitleCommandThenSuccess(){

    }

}