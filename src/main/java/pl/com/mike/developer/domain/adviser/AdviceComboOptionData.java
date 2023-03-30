package pl.com.mike.developer.domain.adviser;

public class AdviceComboOptionData {

    private String value;
    private String text;

    public AdviceComboOptionData(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public AdviceComboOptionData() {
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "AdviceComboOptionData{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
