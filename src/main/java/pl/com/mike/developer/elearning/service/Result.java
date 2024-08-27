package pl.com.mike.developer.elearning.service;

public class Result {
    private final boolean valid;
    private final Reason reason;

    public Result(boolean valid, Reason reason) {
        this.valid = valid;
        this.reason = reason;
    }

    public boolean isValid() {
        return valid;
    }

    public Reason getReason() {
        return reason;
    }

}
