package pl.com.mike.developer.domain;

public class TemplateData {
    private Long id;
    private String name;
    private String contentEmail;
    private String contentSms;
    private Boolean sendEmail;
    private Boolean sendSms;

    public TemplateData(Long id, String name, String contentEmail, String contentSms, Boolean sendEmail, Boolean sendSms) {
        this.id = id;
        this.name = name;
        this.contentEmail = contentEmail;
        this.contentSms = contentSms;
        this.sendEmail = sendEmail;
        this.sendSms = sendSms;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentEmail() {
        return contentEmail;
    }

    public String getContentSms() {
        return contentSms;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public Boolean getSendSms() {
        return sendSms;
    }
}
