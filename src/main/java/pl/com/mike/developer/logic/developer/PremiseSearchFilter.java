package pl.com.mike.developer.logic.developer;

import java.math.BigDecimal;

public class PremiseSearchFilter {
    private Long id;
    private String type;
    private Integer number;
    private Integer floor;
    private Double surfacePerSqMeter;
    private Double pricePerSqMeter;
    private BigDecimal totalPrice;
    private Integer numberOfRooms;
    private String technicalStatus;
    private String salesStatus;
    private String exposure;
    private Boolean isBalcony;
    private Boolean isGarden;
    private Boolean isTerrace;
    private Boolean isLoggia;
    private Integer buildingId;

    public PremiseSearchFilter() {
    }

    public PremiseSearchFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getFloor() {
        return floor;
    }

    public Double getSurfacePerSqMeter() {
        return surfacePerSqMeter;
    }

    public Double getPricePerSqMeter() {
        return pricePerSqMeter;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getTechnicalStatus() {
        return technicalStatus;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public String getExposure() {
        return exposure;
    }

    public Boolean getBalcony() {
        return isBalcony;
    }

    public Boolean getGarden() {
        return isGarden;
    }

    public Boolean getTerrace() {
        return isTerrace;
    }

    public Boolean getLoggia() {
        return isLoggia;
    }

    public Integer getBuildingId() {
        return buildingId;
    }
}
