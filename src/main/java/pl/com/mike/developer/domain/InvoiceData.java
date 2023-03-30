package pl.com.mike.developer.domain;

import pl.com.mike.developer.domain.courseplatform.CourseOrderData;

import java.time.LocalDateTime;

public class InvoiceData {
    private Long id;
    private CourseOrderData order;
    private String type;
    private String fileName;
    private String number;
    private LocalDateTime deleteDate;

    public InvoiceData(Long id, CourseOrderData order, String type, String fileName, String number, LocalDateTime deleteDate) {
        this.id = id;
        this.order = order;
        this.type = type;
        this.fileName = fileName;
        this.number = number;
        this.deleteDate = deleteDate;
    }

    public InvoiceData(CourseOrderData order, String type, String fileName, String number) {
        this.order = order;
        this.type = type;
        this.fileName = fileName;
        this.number = number;
    }

    public InvoiceData(String type, String number) {
        this.type = type;
        this.number = number;
    }

    public InvoiceData(InvoiceData data, LocalDateTime deleteDate) {
        this.id = data.getId();
        this.order = data.getOrder();
        this.type = data.getType();
        this.fileName = data.getFileName();
        this.number = data.getNumber();
        this.deleteDate = deleteDate;
    }

    public Long getId() {
        return id;
    }

    public CourseOrderData getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }
}
