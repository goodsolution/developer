package pl.com.mike.developer.api.courseplatform;

public class TracePostRequest {
    private String what;
    private String value;
    private String who;
    private String browser;
    private String operatingSystem;

    public TracePostRequest() {
    }


    public TracePostRequest(String what, String value, String who) {
        this.what = what;
        this.value = value;
        this.who = who;
    }


    public TracePostRequest(String what, String value, String who, String browser, String operatingSystem) {
        this.what = what;
        this.value = value;
        this.who = who;
        this.browser = browser;
        this.operatingSystem = operatingSystem;
    }


    public String getWhat() {
        return what;
    }

    public String getValue() {
        return value;
    }

    public String getWho() {
        return who;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    @Override
    public String toString() {
        return "TracePostRequest{" +
                "what='" + what + '\'' +
                ", value='" + value + '\'' +
                ", who='" + who + '\'' +
                ", browser='" + browser + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                '}';
    }
}
