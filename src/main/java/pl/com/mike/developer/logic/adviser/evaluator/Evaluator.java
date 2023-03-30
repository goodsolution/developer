package pl.com.mike.developer.logic.adviser.evaluator;

@FunctionalInterface
public interface Evaluator {
    boolean evaluate(Context context, Expression expression);
}
