package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "investments", schema = "course_platform")
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
    @Column(name = "developer_id", insertable = false, updatable = false)
    private Long developerId;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "voivodeship_id")
    private Long voivodeshipId;

    @ManyToOne
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    private DeveloperData developer;

    public InvestmentData() {
    }

    public DeveloperData getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperData developer) {
        this.developer = developer;
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

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
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
        if (!(o instanceof InvestmentData)) return false;
        InvestmentData data = (InvestmentData) o;
        return Objects.equals(getId(), data.getId()) && Objects.equals(getName(), data.getName()) && Objects.equals(getDescription(), data.getDescription()) && Objects.equals(getAddressCountry(), data.getAddressCountry()) && Objects.equals(getAddressStreet(), data.getAddressStreet()) && Objects.equals(getDeveloperId(), data.getDeveloperId()) && Objects.equals(getCityId(), data.getCityId()) && Objects.equals(getVoivodeshipId(), data.getVoivodeshipId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAddressCountry(), getAddressStreet(), getDeveloperId(), getCityId(), getVoivodeshipId());
    }
}
