package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.FileData;

public class FileGetResponse {
    private Long id;
    private String name;
    private String originalName;
    private String contentType;
    private Long sizeInBytes;

    public FileGetResponse(FileData data) {
        this.id = data.getId();
        this.name = data.getName();
        this.originalName = data.getOriginalName();
        this.contentType = data.getContentType();
        this.sizeInBytes = data.getSizeInBytes();
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
