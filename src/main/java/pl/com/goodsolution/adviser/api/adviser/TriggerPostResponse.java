package pl.com.goodsolution.adviser.api.adviser;

public class TriggerPostResponse {
    private TriggeredAdviceGetResponse data;

    public TriggerPostResponse(TriggeredAdviceGetResponse data) {
        this.data = data;
    }

    public TriggeredAdviceGetResponse getData() {
        return data;
    }
}
