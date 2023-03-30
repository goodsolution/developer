package pl.com.mike.developer.api.adviser;

public class AdviceAnswerOptionGetResponse {
    private Long id;
    private Long adviceId;
    private String name;
    private String value;
    private Long order;

    public AdviceAnswerOptionGetResponse(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Long getOrder() {
        return order;
    }
}
