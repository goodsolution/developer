package pl.com.mike.developer.logic.itube;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ITubeFiltersDictionary {
    @JsonProperty("title_pl")
    private String titlePl;
    @JsonProperty("title_en")
    private String titleEn;
    private String keywords;

    public ITubeFiltersDictionary(String titlePl, String titleEn, String keywords) {
        this.titlePl = titlePl;
        this.titleEn = titleEn;
        this.keywords = keywords;
    }

    public ITubeFiltersDictionary() {
    }

    public String getTitlePl() {
        return titlePl;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getKeywords() {
        return keywords;
    }
}
