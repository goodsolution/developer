package pl.com.mike.developer.elearning.service;

import org.springframework.stereotype.Component;
import pl.com.mike.developer.elearning.TaskDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TaskParser {

    private static final Pattern PATTERN = Pattern.compile(
            "#title\\.pl\\(\\)\\{\"(.*?)\"\\};\\s*" +
                    "#title\\.en\\(\\)\\{\"(.*?)\"\\};\\s*" +
                    "#description\\.pl\\(\\)\\{\"(.*?)\"\\};\\s*" +
                    "#description\\.en\\(\\)\\{\"(.*?)\"\\};"
    );

    public TaskDto parseContent(String text) {
        if(text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        Matcher matcher = PATTERN.matcher(text);
        if (matcher.find()) {
            String titlePl = matcher.group(1);
            String titleEn = matcher.group(2);
            String descriptionPl = matcher.group(3);
            String descriptionEn = matcher.group(4);

            return new TaskDto(titlePl, titleEn, descriptionPl, descriptionEn);
        }
        else {
            throw new IllegalArgumentException("Invalid input");
        }
    }

}
