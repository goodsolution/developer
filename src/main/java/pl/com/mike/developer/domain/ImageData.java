package pl.com.mike.developer.domain;

public class ImageData {
    private String fileName;
    private String path;
    private Long id;
    private Long[] dietIds = new Long[0];
    private GalleryImageKind kind;
    private String status;

    public ImageData(ImageData image, Long[] dietIds) {
        this.fileName = image.getFileName();
        this.path = image.getPath();
        this.id = image.getId();
        this.dietIds = dietIds;
        this.kind = image.getKind();
        this.status = image.getStatus();
    }

    public ImageData(String fileName) {
        this.fileName = fileName;
    }

    public ImageData(String fileName, Long id, GalleryImageKind kind, String status) {
        this.fileName = fileName;
        this.id = id;
        this.kind = kind;
        this.status = status;
    }

    public ImageData(String fileName, Long id, Long[] dietIds, GalleryImageKind kind) {
        this.fileName = fileName;
        this.id = id;
        this.dietIds = dietIds;
        this.kind = kind;
    }

    public ImageData(String fileName, Long[] dietIds, GalleryImageKind kind) {
        this.fileName = fileName;
        this.dietIds = dietIds;
        this.kind = kind;
    }

    public ImageData(Long id, String fileName, String path) {
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

    public Long[] getDietIds() {
        return dietIds;
    }

    public GalleryImageKind getKind() {
        return kind;
    }

    public String getStatus() {
        return status;
    }
}
