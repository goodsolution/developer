package pl.com.goodsolution.adviser.domain.adviser;

import java.time.LocalDateTime;

public class TriggeredAdviceLogData {
    private Long id;
    private Long adviceId;
    private Long triggeredAdviceId;
    private String appId;
    private String domain;
    private String message;
    private LocalDateTime dateTime;

    public TriggeredAdviceLogData(Long adviceId, Long triggeredAdviceId, String appId, String domain, String message) {
        this.adviceId = adviceId;
        this.triggeredAdviceId = triggeredAdviceId;
        this.appId = appId;
        this.domain = domain;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public Long getTriggeredAdviceId() {
        return triggeredAdviceId;
    }

    public String getAppId() {
        return appId;
    }

    public String getDomain() {
        return domain;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
