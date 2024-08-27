package pl.com.mike.developer.elearning.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public class CommandValidator implements Validator {

    private final TitleValidator titleValidator;
    private final DescriptionValidator descriptionValidator;
    private final Logger logger = LoggerFactory.getLogger(CommandValidator.class);

    public CommandValidator(TitleValidator titleValidator, DescriptionValidator descriptionValidator) {
        this.titleValidator = titleValidator;
        this.descriptionValidator = descriptionValidator;
    }

    @Override
    public Result validate(String text) {
        if (text == null || text.isEmpty()) {
            logger.warn("Validation failed: input text is null or empty.");
            return new Result(false, Reason.UNKNOWN);
        }

        List<String> lines = splitIntoLines(text);

        for (String line : lines) {
            logger.info("Processing line: {}", line);

            Result result = validateLine(line);

            if (!result.isValid()) {
                getError(result.getReason().toString());
                return result;
            }
        }
        return new Result(true, Reason.OK);
    }

    private Result validateLine(String line) {
        Command[] values = Command.values();
        for (Command value : values) {
            if (line.startsWith("#" + value.getValue())) {
                logger.info("Processing command: {}", value);
                return validateCommand(line, value);
            }
        }
        getError(line);
        return new Result(false, Reason.UNKNOWN);
    }

    private void getError(String text) {
        logger.error("Validation failed: {}", text);
    }

    private Result validateCommand(String line, Command value) {
        return switch (value) {
            case TITLE -> titleValidator.validate(line);
            case DESCRIPTION -> descriptionValidator.validate(line);
        };
    }

    private List<String> splitIntoLines(String text) {
        return Stream.of(text.split("(?<=;)(?=\\s*#)"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

}