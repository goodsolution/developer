package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "buildings", schema = "course_platform")
public class BuildingData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "address_country")
    private String addressCountry;
    @Column(name = "address_street")
    private String addressStreet;
    @Column(name = "address_building_number")
    private String addressBuildingNumber;
    @Column(name = "address_postal_code")
    private String addressPostalCode;
    @Column(name = "investment_id")
    private Long investmentId;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "voivodeship_id")
    private Long voivodeshipId;
    @OneToMany(mappedBy = "building")
    List<PremiseData> premises;

    public BuildingData() {
    }

    public List<PremiseData> getPremises() {
        return premises;
    }

    public void setPremises(List<PremiseData> premises) {
        this.premises = premises;
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

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getVoivodeshipId() {
        return voivodeshipId;
    }

    public void setVoivodeshipId(Long voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildingData)) return false;
        BuildingData that = (BuildingData) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddressCountry(), that.getAddressCountry()) && Objects.equals(getAddressStreet(), that.getAddressStreet()) && Objects.equals(getAddressBuildingNumber(), that.getAddressBuildingNumber()) && Objects.equals(getAddressPostalCode(), that.getAddressPostalCode()) && Objects.equals(getInvestmentId(), that.getInvestmentId()) && Objects.equals(getCityId(), that.getCityId()) && Objects.equals(getVoivodeshipId(), that.getVoivodeshipId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddressCountry(), getAddressStreet(), getAddressBuildingNumber(), getAddressPostalCode(), getInvestmentId(), getCityId(), getVoivodeshipId());
    }
}
