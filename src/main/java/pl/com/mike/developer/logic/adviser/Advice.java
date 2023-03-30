package pl.com.mike.developer.logic.adviser;

public interface Advice {
    TriggeredResult triggered(AdviceContext ctx);

}
