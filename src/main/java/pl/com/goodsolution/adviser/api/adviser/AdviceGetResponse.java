package pl.com.goodsolution.adviser.api.adviser;

import pl.com.goodsolution.adviser.domain.adviser.AdviceData;

public class AdviceGetResponse {
    private Long id;
    private String appId;
    private String domain;
    private String content;
    private String type;
    private String scope;
    private String action;
    private String dataType;
    private String component;
    private String adviceClass;
    private String name;

    public AdviceGetResponse(Long id, String name, String appId, String domain) {
        this.id = id;
        this.name = name;
        this.appId = appId;
        this.domain = domain;
    }

    public AdviceGetResponse(AdviceData data) {
//        this.id = data.getId();
//        this.appId = data.getAppId();
//        this.domain = data.getDomain();
//        this.content = data.getContent();
//        this.type = data.getType().code();
//        this.scope = data.getScope();
//        this.action = data.getAction();
//        this.dataType = data.getDataType();
//        this.component = data.getComponent();
//        this.adviceClass = data.getAdviceClass();
//        this.name = data.getName();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public String getAppId() {
//        return appId;
//    }
//
//    public String getDomain() {
//        return domain;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getScope() {
//        return scope;
//    }
//
//    public String getAction() {
//        return action;
//    }
//
//    public String getDataType() {
//        return dataType;
//    }
//
//    public String getComponent() {
//        return component;
//    }
//
//    public String getAdviceClass() {
//        return adviceClass;
//    }
//
//    public String getName() {
//        return name;
//    }
}
