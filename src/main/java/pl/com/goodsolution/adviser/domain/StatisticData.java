package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StatisticData {
    /*
    L.p.	Klient	Zamówienie	Kierowca	Data	Kwota
     */
    private Long id;
    private Long no;
    private String customer;
    private String orderNumber;
    private String driver;
    private LocalDate date;
    private BigDecimal value;
    private Long ordersCount;
    private String customerEmail;

    public StatisticData(Long id, Long no, String customer, String orderNumber, String driver, LocalDate date, BigDecimal value, Long ordersCount, String customerEmail) {
        this.id = id;
        this.no = no;
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.driver = driver;
        this.date = date;
        this.value = value;
        this.ordersCount = ordersCount;
        this.customerEmail = customerEmail;
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

    public String getDriver() {
        return driver;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Long getId() {
        return id;
    }
}
