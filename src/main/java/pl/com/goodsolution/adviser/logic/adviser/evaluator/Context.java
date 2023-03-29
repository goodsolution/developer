package pl.com.goodsolution.adviser.logic.adviser.evaluator;

import java.util.Map;

public class Context {
    private Map<String, VariableValue> variables;

    public Context(Map<String, VariableValue> variables) {
        this.variables = variables;
    }

    public Map<String, VariableValue> getVariables() {
        return variables;
    }

}
