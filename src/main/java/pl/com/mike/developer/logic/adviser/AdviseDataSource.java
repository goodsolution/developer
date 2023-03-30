package pl.com.mike.developer.logic.adviser;

import java.util.HashMap;
import java.util.Map;

public class AdviseDataSource {

    /*

        user.login

     */

    private Map<String, Map<String, String>> params = new HashMap<>();

    public void add(String context, String param, String value) {
        // TODO
    }

    public String get(String context, String param) {
        return "TODO";// TODO
    }

    @Override
    public String toString() {
        return "AdviseDataSource{" +
                "params=" + params +
                '}';
    }
}
