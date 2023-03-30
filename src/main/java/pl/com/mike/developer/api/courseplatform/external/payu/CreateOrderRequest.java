package pl.com.mike.developer.api.courseplatform.external.payu;

import java.math.BigInteger;
import java.util.List;

public class CreateOrderRequest {
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private BigInteger totalAmount;
    private CreateOrderRequestBuyer buyer;
    private List<CreateOrderRequestProduct> products;
    private String continueUrl;
    private Long extOrderId;
    private String notifyUrl;

    public CreateOrderRequest(String customerIp, String merchantPosId, String description, String currencyCode, BigInteger totalAmount, CreateOrderRequestBuyer buyer, List<CreateOrderRequestProduct> products, String continueUrl, Long extOrderId, String notifyUrl) {
        this.customerIp = customerIp;
        this.merchantPosId = merchantPosId;
        this.description = description;
        this.currencyCode = currencyCode;
        this.totalAmount = totalAmount;
        this.buyer = buyer;
        this.products = products;
        this.continueUrl = continueUrl;
        this.extOrderId = extOrderId;
        this.notifyUrl = notifyUrl;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigInteger getTotalAmount() {
        return totalAmount;
    }

    public CreateOrderRequestBuyer getBuyer() {
        return buyer;
    }

    public List<CreateOrderRequestProduct> getProducts() {
        return products;
    }

    public String getContinueUrl() {
        return continueUrl;
    }

    public Long getExtOrderId() {
        return extOrderId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}
