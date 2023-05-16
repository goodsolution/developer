package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="buildings")
public class BuildingData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "address_country")
    private String addressCountry;
    @Column(name = "address_voivodeship")
    private String addressVoivodeship;
    @Column(name = "address_city")
    private String addressCity;
    @Column(name = "address_street")
    private String addressStreet;
    @Column(name = "address_building_number")
    private String addressBuildingNumber;
    @Column(name = "address_postal_code")
    private String addressPostalCode;
    @OneToMany(mappedBy = "buildingData")
    Set<PremiseData> premises;
    @ManyToOne
    @JoinColumn(name = "investment_id", nullable = false)
    private InvestmentData investmentData;

    public BuildingData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressVoivodeship() {
        return addressVoivodeship;
    }

    public void setAddressVoivodeship(String addressVoivodeship) {
        this.addressVoivodeship = addressVoivodeship;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressBuildingNumber() {
        return addressBuildingNumber;
    }

    public void setAddressBuildingNumber(String addressBuildingNumber) {
        this.addressBuildingNumber = addressBuildingNumber;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public Set<PremiseData> getPremises() {
        return premises;
    }

    public void setPremises(Set<PremiseData> premises) {
        this.premises = premises;
    }

    public InvestmentData getInvestmentData() {
        return investmentData;
    }

    public void setInvestmentData(InvestmentData investmentData) {
        this.investmentData = investmentData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildingData)) return false;
        BuildingData that = (BuildingData) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddressCountry(), that.getAddressCountry()) && Objects.equals(getAddressVoivodeship(), that.getAddressVoivodeship()) && Objects.equals(getAddressCity(), that.getAddressCity()) && Objects.equals(getAddressStreet(), that.getAddressStreet()) && Objects.equals(getAddressBuildingNumber(), that.getAddressBuildingNumber()) && Objects.equals(getAddressPostalCode(), that.getAddressPostalCode()) && Objects.equals(getPremises(), that.getPremises()) && Objects.equals(getInvestmentData(), that.getInvestmentData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddressCountry(), getAddressVoivodeship(), getAddressCity(), getAddressStreet(), getAddressBuildingNumber(), getAddressPostalCode(), getPremises(), getInvestmentData());
    }

}
