package pl.com.goodsolution.adviser.logic.itube;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.itube.ITubeData;

import java.util.regex.Pattern;

@Service
public class Validator {
    private final static Pattern PATTERN_WWW = Pattern.compile("^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+");

    public void validateWWW(ITubeData data) {
        if (data == null || !(PATTERN_WWW.matcher(data.getLink()).matches())) {
            throw new IllegalArgumentException("Web address is not valid");
        }
    }

    public void validateTitlePl(ITubeData data) {
        String titlePl = data.getTitlePl();
        if (titlePl == null || titlePl.equals("")) {
            throw new IllegalArgumentException("Polish title can't be empty");
        } else if (titlePl.length() > 200) {
            throw new IllegalArgumentException("Polish title too long, max 200 characters");
        }
    }

    public void validateTitleEn(ITubeData data) {
        String titleEn = data.getTitleEn();
        if (titleEn == null || titleEn.equals("")) {
            throw new IllegalArgumentException("English title can't be empty");
        } else if (titleEn.length() > 200) {
            throw new IllegalArgumentException("English title too long, max 200 characters");
        }
    }

    public void validateKeyWords(ITubeData data) {
        String keyWords = data.getKeywords();
        if (keyWords.length() > 200) {
            throw new IllegalArgumentException("Keywords too long, max 200 characters");
        }
    }

}
