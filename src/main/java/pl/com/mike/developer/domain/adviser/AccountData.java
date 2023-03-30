package pl.com.mike.developer.domain.adviser;

public class AccountData {
    private Long id;
    private AccountType type;
    private Integer preferredHourFrom;
    private Integer preferredHourTo;
    private Boolean allDay;
    private String name;
    private Boolean newsletterAccepted;
    private Boolean regulationsAccepted;

    public AccountData(Long id, Integer preferredHourFrom, Integer preferredHourTo, Boolean allDay, Boolean newsletterAccepted, String name, AccountType type) {
        this.id = id;
        this.preferredHourFrom = preferredHourFrom;
        this.preferredHourTo = preferredHourTo;
        this.allDay = allDay;
        this.newsletterAccepted = newsletterAccepted;
        this.name = name;
        this.type = type;
    }

    public AccountData(Long id, AccountType type, Integer preferredHourFrom, Integer preferredHourTo, Boolean allDay, String name, Boolean newsletterAccepted, Boolean regulationsAccepted) {
        this.id = id;
        this.type = type;
        this.preferredHourFrom = preferredHourFrom;
        this.preferredHourTo = preferredHourTo;
        this.allDay = allDay;
        this.name = name;
        this.newsletterAccepted = newsletterAccepted;
        this.regulationsAccepted = regulationsAccepted;
    }

    public AccountData(AccountType type, Integer preferredHourFrom, Integer preferredHourTo, Boolean allDay, String name) {
        this.type = type;
        this.preferredHourFrom = preferredHourFrom;
        this.preferredHourTo = preferredHourTo;
        this.allDay = allDay;
        this.name = name;
    }

    public AccountData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountData(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
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

    public Boolean getRegulationsAccepted() {
        return regulationsAccepted;
    }
}
