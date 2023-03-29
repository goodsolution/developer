package pl.com.goodsolution.adviser.domain;

import java.io.ByteArrayInputStream;

public class ExcelMenuPreviewData {
    private ByteArrayInputStream data;
    private long size;
    private String fileName;

    public ExcelMenuPreviewData(ByteArrayInputStream data, long size, String fileName) {
        this.data = data;
        this.size = size;
        this.fileName = fileName;
    }

    public ByteArrayInputStream getData() {
        return data;
    }

    public long getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }
}
