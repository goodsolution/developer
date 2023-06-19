package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "investments")
public class InvestmentData implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @Column(name = "address_country")
    private String addressCountry;
    @Column(name = "address_street")
    private String addressStreet;
    @ManyToOne
    @JoinColumn(name = "developer_id", nullable = false)
    private DeveloperData developerData;
    @OneToMany(mappedBy = "investmentData")
    private Set<BuildingData> buildings;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityData cityData;
    @ManyToOne
    @JoinColumn(name = "voivodeship_id", nullable = false)
    private VoivodeshipData voivodeshipData;

    public InvestmentData() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public DeveloperData getDeveloperData() {
        return developerData;
    }

    public void setDeveloperData(DeveloperData developerData) {
        this.developerData = developerData;
    }

    public Set<BuildingData> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<BuildingData> buildings) {
        this.buildings = buildings;
    }

    public CityData getCityData() {
        return cityData;
    }

    public void setCityData(CityData cityData) {
        this.cityData = cityData;
    }

    public VoivodeshipData getVoivodeshipData() {
        return voivodeshipData;
    }

    public void setVoivodeshipData(VoivodeshipData voivodeshipData) {
        this.voivodeshipData = voivodeshipData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestmentData)) return false;
        InvestmentData data = (InvestmentData) o;
        return Objects.equals(getId(), data.getId()) && Objects.equals(getName(), data.getName()) && Objects.equals(getDescription(), data.getDescription()) && Objects.equals(getAddressCountry(), data.getAddressCountry()) && Objects.equals(getAddressStreet(), data.getAddressStreet()) && Objects.equals(getDeveloperData(), data.getDeveloperData()) && Objects.equals(getBuildings(), data.getBuildings()) && Objects.equals(getCityData(), data.getCityData()) && Objects.equals(getVoivodeshipData(), data.getVoivodeshipData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAddressCountry(), getAddressStreet(), getDeveloperData(), getBuildings(), getCityData(), getVoivodeshipData());
    }
}
