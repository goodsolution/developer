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
    private String technicalStatusTranslation;
    private String salesStatusTranslation;
    private String exposureTranslation;
    private String languageCode;
    private String priceFunction;

    public PremiseSearchFilter(Long id, String languageCode) {
        this.id = id;
        this.languageCode = languageCode;
    }

    public PremiseSearchFilter(Long id, String priceFunction, String languageCode) {
        this.id = id;
        this.priceFunction = priceFunction;
        this.languageCode = languageCode;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setSurfacePerSqMeter(Double surfacePerSqMeter) {
        this.surfacePerSqMeter = surfacePerSqMeter;
    }

    public void setPricePerSqMeter(Double pricePerSqMeter) {
        this.pricePerSqMeter = pricePerSqMeter;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setTechnicalStatus(String technicalStatus) {
        this.technicalStatus = technicalStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public void setBalcony(Boolean balcony) {
        isBalcony = balcony;
    }

    public void setGarden(Boolean garden) {
        isGarden = garden;
    }

    public void setTerrace(Boolean terrace) {
        isTerrace = terrace;
    }

    public void setLoggia(Boolean loggia) {
        isLoggia = loggia;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getTechnicalStatusTranslation() {
        return technicalStatusTranslation;
    }

    public void setTechnicalStatusTranslation(String technicalStatusTranslation) {
        this.technicalStatusTranslation = technicalStatusTranslation;
    }

    public String getSalesStatusTranslation() {
        return salesStatusTranslation;
    }

    public void setSalesStatusTranslation(String salesStatusTranslation) {
        this.salesStatusTranslation = salesStatusTranslation;
    }

    public String getExposureTranslation() {
        return exposureTranslation;
    }

    public void setExposureTranslation(String exposureTranslation) {
        this.exposureTranslation = exposureTranslation;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getPriceFunction() {
        return priceFunction;
    }

    public void setPriceFunction(String priceFunction) {
        this.priceFunction = priceFunction;
    }
}
