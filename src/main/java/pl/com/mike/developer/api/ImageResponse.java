package pl.com.mike.developer.api;

public class ImageResponse {
    private String fileName;
    private String path;
    private Long id;

    public ImageResponse(Long id, String fileName, String path) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public Long getId() {
        return id;
    }
}
