package pl.com.mike.developer.domain.adviser;

import java.time.LocalDateTime;

public class AdvicesFilter {
    private Long page;
    private Long pageSize;
    private String nameAsSubstring;
    private String appIdAsSubstring;
    private String domainAsSubstring;
    private String name;
    private String appId;
    private String domain;
    private String type;
    private String scope;
    private String action;
    private LocalDateTime startDateFrom;
    private LocalDateTime startDateTo;
    private LocalDateTime stopDateFrom;
    private LocalDateTime stopDateTo;
    private AdviceStatus status;

    public AdvicesFilter(AdviceStatus status) {
        this.status = status;
    }

    public AdvicesFilter(String appIdAsSubstring, String domainAsSubstring, String type, String scope, String action) {
        this.appIdAsSubstring = appIdAsSubstring;
        this.domainAsSubstring = domainAsSubstring;
        this.type = type;
        this.scope = scope;
        this.action = action;
    }

    public AdvicesFilter(String appId, String domain, String type, String scope, LocalDateTime startDateTo, LocalDateTime stopDateFrom, AdviceStatus status) {
        this.appId = appId;
        this.domain = domain;
        this.type = type;
        this.scope = scope;
        this.startDateTo = startDateTo;
        this.stopDateFrom = stopDateFrom;
        this.status = status;
    }

    public AdvicesFilter(Long page, Long pageSize, String nameAsSubstring, String appIdAsSubstring, String domainAsSubstring) {
        this.page = page;
        this.pageSize = pageSize;
        this.nameAsSubstring = nameAsSubstring;
        this.appIdAsSubstring = appIdAsSubstring;
        this.domainAsSubstring = domainAsSubstring;
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

    public String getDomain() {
        return domain;
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
}
