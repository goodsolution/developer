package pl.com.goodsolution.adviser.domain.adviser;

public class AdviceVariableData {

    private Long id;
    private String name;
    private AdviseDataType type;
    private String value;

    public AdviceVariableData(Long id, String name, AdviseDataType type, String value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AdviseDataType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
