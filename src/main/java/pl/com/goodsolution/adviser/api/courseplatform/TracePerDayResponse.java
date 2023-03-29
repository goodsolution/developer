package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class TracePerDayResponse {
    private List<TracePerDayGetResponse> traces;

    public TracePerDayResponse() {
    }

    public TracePerDayResponse(List<TracePerDayGetResponse> traces) {
        this.traces = traces;
    }

    public List<TracePerDayGetResponse> getTraces() {
        return traces;
    }
}
