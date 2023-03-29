package pl.com.goodsolution.adviser.api.adviser;

public class TriggerPostRequest {
    private ContextRequest context;
    private DataSourceRequest dataSource;
    private String action;

    public TriggerPostRequest() {
    }

    public TriggerPostRequest(ContextRequest context, DataSourceRequest dataSource, String action) {
        this.context = context;
        this.dataSource = dataSource;
        this.action = action;
    }

    public ContextRequest getContext() {
        return context;
    }

    public DataSourceRequest getDataSource() {
        return dataSource;
    }

    public String getAction() {
        return action;
    }
}
