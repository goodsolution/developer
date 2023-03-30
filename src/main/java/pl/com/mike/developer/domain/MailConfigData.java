package pl.com.mike.developer.domain;

public class MailConfigData {
    private Long id;
    private String type;
    private String smtpServer;
    private String defaultEmail;
    private Long port;
    private String encryption;
    private String user;
    private String password;
    private String headerFrom;
    private String newOrderEmail;
    private String smtpAuth;
    private String exportEmail;
    private String testEmail;

    public MailConfigData(Long id, String type, String smtpServer, String defaultEmail, Long port, String encryption, String user, String password, String headerFrom, String newOrderEmail, String smtpAuth, String exportEmail, String testEmail) {
        this.id = id;
        this.type = type;
        this.smtpServer = smtpServer;
        this.defaultEmail = defaultEmail;
        this.port = port;
        this.encryption = encryption;
        this.user = user;
        this.password = password;
        this.headerFrom = headerFrom;
        this.newOrderEmail = newOrderEmail;
        this.smtpAuth = smtpAuth;
        this.exportEmail = exportEmail;
        this.testEmail = testEmail;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public Long getPort() {
        return port;
    }

    public String getEncryption() {
        return encryption;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHeaderFrom() {
        return headerFrom;
    }

    public String getNewOrderEmail() {
        return newOrderEmail;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public String getExportEmail() {
        return exportEmail;
    }

    public String getTestEmail() {
        return testEmail;
    }
}
