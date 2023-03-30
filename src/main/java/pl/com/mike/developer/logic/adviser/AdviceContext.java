package pl.com.mike.developer.logic.adviser;

import pl.com.mike.developer.domain.adviser.AdviceData;

public class AdviceContext {
    private String id;
    private AdviseDataSource dataSource;
    private AdviceData advice;

    public AdviceContext(String id, AdviseDataSource dataSource, AdviceData advice) {
        this.id = id;
        this.dataSource = dataSource;
        this.advice = advice;
    }

    public String getId() {
        return id;
    }

    public AdviseDataSource getDataSource() {
        return dataSource;
    }

    public AdviceData getAdvice() {
        return advice;
    }

    @Override
    public String toString() {
        return "AdviceContext{" +
                "id='" + id + '\'' +
                ", dataSource=" + dataSource +
                ", advice=" + advice +
                '}';
    }
}
