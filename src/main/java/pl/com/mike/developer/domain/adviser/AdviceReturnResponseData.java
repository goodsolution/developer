package pl.com.mike.developer.domain.adviser;

public class AdviceReturnResponseData {
    private String response;

    public AdviceReturnResponseData(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "AdviceReturnResponseData{" +
                "response='" + response + '\'' +
                '}';
    }
}
