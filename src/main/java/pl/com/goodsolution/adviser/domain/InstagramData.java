package pl.com.goodsolution.adviser.domain;

public class InstagramData {
    private String href;
    private String src;
    private String alt;
    private String title;

    public InstagramData(String href, String src, String alt, String title) {
        this.href = href;
        this.src = src;
        this.alt = alt;
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public String getSrc() {
        return src;
    }

    public String getAlt() {
        return alt;
    }

    public String getTitle() {
        return title;
    }
}
