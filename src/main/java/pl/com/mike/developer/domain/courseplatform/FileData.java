package pl.com.mike.developer.domain.courseplatform;

public class FileData {

    private Long id;
    private String name;
    private String originalName;
    private String contentType;
    private Long sizeInBytes;


    public FileData(Long id, String name, String originalName, String contentType, Long sizeInBytes) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
        this.sizeInBytes = sizeInBytes;
    }

    public FileData(String name, String originalName, String contentType, Long sizeInBytes) {
        this.name = name;
        this.originalName = originalName;
        this.contentType = contentType;
        this.sizeInBytes = sizeInBytes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSizeInBytes() {
        return sizeInBytes;
    }
}
