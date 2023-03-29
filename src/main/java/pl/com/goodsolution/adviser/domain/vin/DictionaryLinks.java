package pl.com.goodsolution.adviser.domain.vin;

public class DictionaryLinks {
    private String first;
    private String self;
    private String last;

    public DictionaryLinks(String first, String self, String last) {
        this.first = first;
        this.self = self;
        this.last = last;
    }

    public DictionaryLinks() {
    }

    public String getFirst() {
        return first;
    }

    public String getSelf() {
        return self;
    }

    public String getLast() {
        return last;
    }
}
