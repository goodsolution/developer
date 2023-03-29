package pl.com.goodsolution.adviser.domain.adviser;

import pl.com.goodsolution.adviser.api.adviser.ContextRequest;

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
