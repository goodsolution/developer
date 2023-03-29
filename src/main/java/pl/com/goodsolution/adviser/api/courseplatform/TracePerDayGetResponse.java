package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.TraceData;

import java.time.format.DateTimeFormatter;

public class TracePerDayGetResponse {
    private String date;
    private String what;
    private String value;
    private String total;

    public TracePerDayGetResponse(TraceData traceData, DateTimeFormatter dateTimeFormatter) {
        this.date = traceData.getLocalDateTime().format(dateTimeFormatter);
        this.what = traceData.getWhat();
        this.value = traceData.getValue();
        this.total = traceData.getId().toString();
    }

    public String getDate() {
        return date;
    }

    public String getWhat() {
        return what;
    }

    public String getValue() {
        return value;
    }

    public String getTotal() {
        return total;
    }
}
