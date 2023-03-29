package pl.com.goodsolution.adviser.api.lifeadviser;

public class OptionsGetResponse {

    private Integer preferredHourFrom;
    private Integer preferredHourTo;
    private Boolean allDay;
    private Boolean newsletterAccepted;

    public OptionsGetResponse() {
    }

    public OptionsGetResponse(Integer preferredHourFrom, Integer preferredHourTo, Boolean allDay, Boolean newsletterAccepted) {
        this.preferredHourFrom = preferredHourFrom;
        this.preferredHourTo = preferredHourTo;
        this.allDay = allDay;
        this.newsletterAccepted = newsletterAccepted;
    }

    public Integer getPreferredHourFrom() {
        return preferredHourFrom;
    }

    public Integer getPreferredHourTo() {
        return preferredHourTo;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public Boolean getNewsletterAccepted() {
        return newsletterAccepted;
    }
}
