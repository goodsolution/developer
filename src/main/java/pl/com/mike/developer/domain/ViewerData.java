package pl.com.mike.developer.domain;

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
