package pl.com.mike.developer.logic.courseplatform;

import java.util.HashMap;
import java.util.Map;

public enum MovieLinkType {
    NO_MOVIE("n"),
    YOUTUBE("y"),
    VIMEO("v");

    private String code;

    MovieLinkType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, MovieLinkType> codesWithEnums;

    static {
        codesWithEnums = new HashMap<>();
        for (MovieLinkType codeWithEnum : MovieLinkType.values()) {
            codesWithEnums.put(codeWithEnum.code, codeWithEnum);
        }
    }

    public static MovieLinkType from(String movieLinkType) {
        return codesWithEnums.get(movieLinkType);
    }

}
