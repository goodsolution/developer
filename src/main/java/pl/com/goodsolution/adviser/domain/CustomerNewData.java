package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerNewData {
    /*
    L.p.	Klient	Zamówienie	Kwota	Status zamówienia	Data
     */
    private Long no;
    private String customer;
    private String orderNumber;
    private LocalDate date;
    private BigDecimal value;
    private String orderStatus;

    public CustomerNewData(Long no, String customer, String orderNumber, LocalDate date, BigDecimal value, String orderStatus) {
        this.no = no;
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.date = date;
        this.value = value;
        this.orderStatus = orderStatus;
    }

    public Long getNo() {
        return no;
    }

    public String getCustomer() {
        return customer;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
