package pl.com.mike.developer.api.lifeadviser;

public class SuggestionPostResponse {

    private String suggestion;

    public SuggestionPostResponse(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
