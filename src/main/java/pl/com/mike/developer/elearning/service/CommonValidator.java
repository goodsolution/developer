package pl.com.mike.developer.elearning.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonValidator implements Validator {

    private static final String FULL_PATTERN;

    private static final String COMMAND_PATTERN;

    static {
        COMMAND_PATTERN = Stream.of(Command.values())
                .map(Command::getValue)
                .collect(Collectors.joining("|"));


        FULL_PATTERN = String.format(
                "^\\s*#(%s)\\.pl\\(\\)\\{\\s*\".*?\"\\s*\\};" + // Required line
                        "(\\s*\\r?\\n\\s*#(%s)\\.en\\(\\)\\{\\s*\".*?\"\\s*\\};?" +  // Optional #title.en line
                        "(\\s*\\r?\\n\\s*#(%s)\\.pl\\(\\)\\{\\s*\".*?\"\\s*\\};)?" +  // Optional #description.pl line
                        "(\\s*\\r?\\n\\s*#(%s)\\.en\\(\\)\\{\\s*\".*?\"\\s*\\};)?" +  // Optional #description.en line
                        "\\s*)?$", // End of string, optional trailing whitespace
                COMMAND_PATTERN,COMMAND_PATTERN, COMMAND_PATTERN, COMMAND_PATTERN
        );
    }

    private static final Pattern PATTERN = Pattern.compile(FULL_PATTERN);

    @Override
    public Result validate(String text) {
        if (isNullOrEmpty(text)) {
            return new Result(false, Reason.EMPTY);
        }
        return matchesPattern(text) ? new Result(true, Reason.OK) : new Result(false, Reason.UNKNOWN);
    }

    private boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    private boolean matchesPattern(String text) {
        Matcher matcher = PATTERN.matcher(text);
        return matcher.matches();
    }

//    ^\s*#title.pl\(\)\{\s*".*?"\s*\};\r?\n\s*#title.en\(\)\{\s*".*?"\s*\};\r?\r?\n\s*#description.pl\(\)\{\s*".*?"\s*\};\r?\n\s*#description.en\(\)\{\s*".*?"\s*\}\s*;
    //            "^#title.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\n#title.en\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\r?\\n#description.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\n#description.en\\(\\)\\{\\s*\".*?\"\\s*\\};";
}
