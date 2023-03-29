package pl.com.goodsolution.adviser.api;

public class OrderReleasedDeliveryGetResponse {
    private Long id;
    private Long no;
    private String productName;
    private String driver;
    private String hourFrom;
    private String hourTo;

    public OrderReleasedDeliveryGetResponse(Long id, Long no, String productName, String driver, String hourFrom, String hourTo) {
        this.id = id;
        this.no = no;
        this.productName = productName;
        this.driver = driver;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
    }

    public Long getId() {
        return id;
    }

    public Long getNo() {
        return no;
    }

    public String getProductName() {
        return productName;
    }

    public String getDriver() {
        return driver;
    }

    public String getHourFrom() {
        return hourFrom;
    }

    public String getHourTo() {
        return hourTo;
    }
}
