package pl.com.mike.developer.elearning.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonValidator implements Validator {

    private static final String FULL_PATTERN;

    private static final String COMMAND_PATTERN;

    private final TitleValidator titleValidator = new TitleValidator();
    private final DescriptionValidator descriptionValidator = new DescriptionValidator();
    private final CommandValidator commandValidator = new CommandValidator(titleValidator, descriptionValidator);

    static {
        COMMAND_PATTERN = Stream.of(Command.values())
                .map(Command::getValue)
                .collect(Collectors.joining("|"));

        FULL_PATTERN = String.format(
                "^\\s*#(%s)\\.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\s*" +  // Required .pl line
                        "(#(%s)\\.en\\(\\)\\{\\s*\".*?\"\\s*\\};\\s*)?" +  // Optional .en line
                        "(#(%s)\\.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\s*)?" +  // Optional .pl line
                        "(#(%s)\\.en\\(\\)\\{\\s*\".*?\"\\s*\\};\\s*)?$",  // Optional .en line
                COMMAND_PATTERN, COMMAND_PATTERN, COMMAND_PATTERN, COMMAND_PATTERN
        );
    }


    private static final Pattern PATTERN = Pattern.compile(FULL_PATTERN);

    @Override
    public Result validate(String text) {
        if (isNullOrEmpty(text)) {
            return new Result(false, Reason.EMPTY);
        }
        if(!matchesPattern(text)) {
            return new Result(false, Reason.UNKNOWN);
        }
        return commandValidator.validate(text);
    }

    private boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    private boolean matchesPattern(String text) {
        Matcher matcher = PATTERN.matcher(text);
        return matcher.matches();
    }

}
