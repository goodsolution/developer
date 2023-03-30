package pl.com.mike.developer.logic.adviser;

public class TriggeredResult {
    private boolean result;
    private String content;
    private static TriggeredResult noTriggered = new TriggeredResult(false);

    private TriggeredResult(boolean result) {
        this.result = result;
    }

    private TriggeredResult(boolean result, String content) {
        this.result = result;
        this.content = content;
    }

    public boolean wasTriggered() {
        return result;
    }

    public String getContent() {
        return content;
    }

    public static TriggeredResult triggered(String content) {
        return new TriggeredResult(true, content);
    }

    public static TriggeredResult noTriggered() {
        return noTriggered;
    }
}
