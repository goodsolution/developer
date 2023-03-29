package pl.com.goodsolution.adviser.logic.vin;

public class DictionariesFilter {
    private String id;
    private String limit;
    private String page;


    public DictionariesFilter(String id, String limit, String page) {
        this.id = id;
        this.limit = limit;
        this.page = page;
    }

    public DictionariesFilter(String limit, String page) {
        this.limit = limit;
        this.page = page;
    }

    public DictionariesFilter(String id) {
        this.id = id;
    }

    public DictionariesFilter() {
    }

    public String getLimit() {
        return limit;
    }

    public String getPage() {
        return page;
    }

    public String getId() {
        return id;
    }
}
