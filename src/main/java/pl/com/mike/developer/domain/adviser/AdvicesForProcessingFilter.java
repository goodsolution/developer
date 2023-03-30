package pl.com.mike.developer.domain.adviser;

import java.time.LocalDateTime;

public class AdvicesForProcessingFilter {
    private Long page;
    private Long pageSize;
    private String nameAsSubstring;
    private String appIdAsSubstring;
    private String domainAsSubstring;
    private String name;
    private String appId;
    private String context;
    private String type;
    private String scope;
    private String action;
    private LocalDateTime startDateFrom;
    private LocalDateTime startDateTo;
    private LocalDateTime stopDateFrom;
    private LocalDateTime stopDateTo;
    private AdviceStatus status;
    private Long customerId;


    public AdvicesForProcessingFilter(AdviceStatus status, Long customerId) {
        this.status = status;
        this.customerId = customerId;
    }



    public String getAppIdAsSubstring() {
        return appIdAsSubstring;
    }

    public String getDomainAsSubstring() {
        return domainAsSubstring;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    public String getAction() {
        return action;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getNameAsSubstring() {
        return nameAsSubstring;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getContext() {
        return context;
    }

    public LocalDateTime getStartDateFrom() {
        return startDateFrom;
    }

    public LocalDateTime getStartDateTo() {
        return startDateTo;
    }

    public LocalDateTime getStopDateFrom() {
        return stopDateFrom;
    }

    public LocalDateTime getStopDateTo() {
        return stopDateTo;
    }

    public AdviceStatus getStatus() {
        return status;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
