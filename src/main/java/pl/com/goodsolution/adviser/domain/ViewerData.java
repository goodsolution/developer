package pl.com.goodsolution.adviser.domain;

public class ViewerData {
    private Long id;
    private String httpReferer;

    public ViewerData(Long id, String httpReferer) {
        this.id = id;
        this.httpReferer = httpReferer;
    }

    public Long getId() {
        return id;
    }

    public String getHttpReferer() {
        return httpReferer;
    }
}
