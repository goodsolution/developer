package pl.com.goodsolution.adviser.logic.adviser.evaluator;

@FunctionalInterface
public interface Evaluator {
    boolean evaluate(Context context, Expression expression);
}
