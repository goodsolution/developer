package pl.com.goodsolution.adviser.domain;

public class UploadResult {
    private String fileName;

    public UploadResult(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
