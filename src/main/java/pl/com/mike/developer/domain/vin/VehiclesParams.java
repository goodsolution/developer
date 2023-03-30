package pl.com.mike.developer.domain.vin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehiclesParams {
    private Long id;
    private String voivodeshipCode;
    private LocalDateTime lastRefreshDateTime;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer page;
    private LocalDateTime pageCheck;


    public VehiclesParams(Long id, String voivodeshipCode, LocalDateTime lastRefreshDateTime, LocalDate dateFrom, LocalDate dateTo, Integer page, LocalDateTime pageCheck) {
        this.id = id;
        this.voivodeshipCode = voivodeshipCode;
        this.lastRefreshDateTime = lastRefreshDateTime;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.page = page;
        this.pageCheck = pageCheck;
    }

    public VehiclesParams(String voivodeshipCode, LocalDateTime lastRefreshDateTime, LocalDate dateFrom, LocalDate dateTo, Integer page, LocalDateTime pageCheck) {
        this.voivodeshipCode = voivodeshipCode;
        this.lastRefreshDateTime = lastRefreshDateTime;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.page = page;
        this.pageCheck = pageCheck;
    }

    public VehiclesParams(String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo, Integer page, LocalDateTime pageCheck) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.page = page;
        this.pageCheck = pageCheck;
    }

    public VehiclesParams(String voivodeshipCode, LocalDate dateFrom, LocalDate dateTo, Integer page) {
        this.voivodeshipCode = voivodeshipCode;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.page = page;
    }

    public Long getId() {
        return id;
    }

    public String getVoivodeshipCode() {
        return voivodeshipCode;
    }

    public LocalDateTime getLastRefreshDateTime() {
        return lastRefreshDateTime;
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

    public LocalDateTime getPageCheck() {
        return pageCheck;
    }

    @Override
    public String toString() {
        return voivodeshipCode + " " + dateFrom + " " + dateTo;
    }
}
