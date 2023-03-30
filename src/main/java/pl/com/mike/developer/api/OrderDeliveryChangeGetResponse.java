package pl.com.mike.developer.api;

public class OrderDeliveryChangeGetResponse {
    private Long id;
    private Long no;
    private String type;
    private String dateFrom;
    private String dateTo;
    private String productName;
    private String userName;
    private String dateTime;


    public OrderDeliveryChangeGetResponse(Long id, Long no, String type, String dateFrom, String dateTo, String productName, String userName, String dateTime) {
        this.id = id;
        this.no = no;
        this.type = type;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.productName = productName;
        this.userName = userName;
        this.dateTime = dateTime;
    }


    public Long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Long getId() {
        return id;
    }
}
