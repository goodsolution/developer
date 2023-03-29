package pl.com.goodsolution.adviser.domain.adviser;

import pl.com.goodsolution.adviser.logic.adviser.JdbcUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TriggeredAdvicesFilter {
    private Long id;
    private Long adviceId;
    private Long customerId;
    private TriggeredAdviceStatus status;
    private String contextId;
    private String type;
    private String lang;
    private String scope;
    private String action;
    private Long page;
    private Long pageSize;
    private String nameAsSubstring;
    private String appIdAsSubstring;
    private String domainAsSubstring;
    private String appId;
    private String context;
    private LocalDateTime triggerDateTimeFrom;
    private LocalDateTime triggerDateTimeTo;
    private JdbcUtil.SortColumns sortColumns;
    private String mode;

    public TriggeredAdvicesFilter(Long adviceId) {
        this.adviceId = adviceId;
    }

    public TriggeredAdvicesFilter(String status, Long customerId, Long page, Long pageSize, String appId, String context, String mode) {
        this.status = TriggeredAdviceStatus.fromCode(status);
        this.context = context;
        this.appId = appId;
        this.sortColumns = sortColumns;
        this.mode = mode;
    }

    public TriggeredAdvicesFilter(String contextId, Long page, Long pageSize, String appId, String context, JdbcUtil.SortColumns sortColumns) {
        this.contextId = contextId;
        this.customerId = customerId;
        this.page = page;
        this.pageSize = pageSize;
        this.appId = appId;
        this.context = context;
    }

    public TriggeredAdvicesFilter(Long customerId, TriggeredAdviceStatus status, String appId, String context, JdbcUtil.SortColumns sortColumns) {
        this.customerId = customerId;
        this.status = status;
        this.appId = appId;
        this.context = context;
        this.sortColumns = sortColumns;
    }

    public TriggeredAdvicesFilter(String contextId, Long page, Long pageSize, String appId, String context, JdbcUtil.SortColumns sortColumns, Long score) {
        this.contextId = contextId;
        this.page = page;
        this.pageSize = pageSize;
        this.appId = appId;
        this.context = context;
        this.sortColumns = sortColumns;
    }

    public TriggeredAdvicesFilter(Long page, Long pageSize, String nameAsSubstring, String appIdAsSubstring, String domainAsSubstring, LocalDateTime triggerDateTimeFrom, LocalDateTime triggerDateTimeTo) {
        this.page = page;
        this.pageSize = pageSize;
        this.nameAsSubstring = nameAsSubstring;
        this.appIdAsSubstring = appIdAsSubstring;
        this.domainAsSubstring = domainAsSubstring;
        this.triggerDateTimeFrom = triggerDateTimeFrom;
        this.triggerDateTimeTo = triggerDateTimeTo;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public String getAppIdAsSubstring() {
        return appIdAsSubstring;
    }

    public String getDomainAsSubstring() {
        return domainAsSubstring;
    }

    public String getContextId() {
        return contextId;
    }

    public String getType() {
        return type;
    }

    public String getLang() {
        return lang;
    }

    public String getScope() {
        return scope;
    }

    public String getAction() {
        return action;
    }

    public String getNameAsSubstring() {
        return nameAsSubstring;
    }

    public LocalDateTime getTriggerDateTimeFrom() {
        return triggerDateTimeFrom;
    }

    public LocalDateTime getTriggerDateTimeTo() {
        return triggerDateTimeTo;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public JdbcUtil.SortColumns getSortColumns() {
        return sortColumns;
    }

    public String getAppId() {
        return appId;
    }

    public String getContext() {
        return context;
    }


    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public TriggeredAdviceStatus getStatus() {
        return status;
    }

    public String getMode() {
        return mode;
    }
}
