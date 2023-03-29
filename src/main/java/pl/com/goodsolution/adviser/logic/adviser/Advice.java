package pl.com.goodsolution.adviser.logic.adviser;

public interface Advice {
    TriggeredResult triggered(AdviceContext ctx);

}
