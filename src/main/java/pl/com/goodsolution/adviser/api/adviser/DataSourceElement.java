package pl.com.goodsolution.adviser.api.adviser;

public class DataSourceElement {
    private String parameter;
    private String value;

    public DataSourceElement(String parameter, String value) {
        this.parameter = parameter;
        this.value = value;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }
}
