package pl.com.mike.developer.web;

public class TranslationGetResponse {
    private String translation;

    public TranslationGetResponse(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

}