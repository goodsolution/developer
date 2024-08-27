package pl.com.mike.developer.elearning.service;

public enum Command {

    TITLE("title"),
    DESCRIPTION("description");

    private final String command;

    Command(String value) {
        this.command = value;
    }

    public String getValue() {
        return command;
    }


}
