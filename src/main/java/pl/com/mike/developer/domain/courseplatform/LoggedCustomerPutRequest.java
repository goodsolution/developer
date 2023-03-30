package pl.com.mike.developer.domain.courseplatform;

public class LoggedCustomerPutRequest {
    private String language;
    private Boolean newsletterAccepted;

    public LoggedCustomerPutRequest() {
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }
}
