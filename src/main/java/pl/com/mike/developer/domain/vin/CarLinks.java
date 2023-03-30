package pl.com.mike.developer.domain.vin;

public class CarLinks {
    private String self;
    private String first;
    private String next;
    private String last;


    public CarLinks(String self) {
        this.self = self;
    }

    public CarLinks(String self, String first, String next, String last) {
        this.self = self;
        this.first = first;
        this.next = next;
        this.last = last;
    }

    public CarLinks() {
    }

    public String getSelf() {
        return self;
    }

    public String getFirst() {
        return first;
    }

    public String getNext() {
        return next;
    }

    public String getLast() {
        return last;
    }
}
