package pl.com.mike.developer.domain;

public class OrderDiscountCodeUsageData {
    private Long orderId;
    private String code;
    private String type;
    private Long codeId;
    private String ip;

    public OrderDiscountCodeUsageData(Long orderId, String code, String type, Long codeId, String ip) {
        this.orderId = orderId;
        this.code = code;
        this.type = type;
        this.codeId = codeId;
        this.ip = ip;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public Long getCodeId() {
        return codeId;
    }

    public String getIp() {
        return ip;
    }
}
