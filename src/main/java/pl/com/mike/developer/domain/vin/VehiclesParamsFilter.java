package pl.com.mike.developer.domain.vin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehiclesParamsFilter {
    private Long id;
    private LocalDateTime lastRefreshDateTime;
    private String voivodeshipCode;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    private Integer page;

    private LocalDateTime page_check;


    public VehiclesParamsFilter(String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public VehiclesParamsFilter(String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo, Integer page, LocalDateTime page_check) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.page = page;
        this.page_check = page_check;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLastRefreshDateTime() {
        return lastRefreshDateTime;
    }

    public String getVoivodeshipCode() {
        return voivodeshipCode;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Integer getPage() {
        return page;
    }

    public LocalDateTime getPage_check() {
        return page_check;
    }

}