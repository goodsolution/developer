package pl.com.goodsolution.adviser.domain.adviser;

public class AdviceAnswerOptionData {
    private Long id;
    private Long adviceId;
    private String name;
    private String value;
    private Long order;


    public AdviceAnswerOptionData(Long id, Long adviceId, String name, String value, Long order) {
        this.id = id;
        this.adviceId = adviceId;
        this.name = name;
        this.value = value;
        this.order = order;
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

    @Override
    public String toString() {
        return "AdviceAnswerOptionData{" +
                "id=" + id +
                ", adviceId=" + adviceId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", order=" + order +
                '}';
    }
}
