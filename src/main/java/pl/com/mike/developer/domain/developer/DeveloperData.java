package pl.com.mike.developer.domain.developer;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developers")
public class DeveloperData implements Serializable {
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
    @Column(name = "address_flat_number")
    private String addressFlatNumber;
    @Column(name = "address_postal_code")
    private String addressPostalCode;
    @Column(name = "telephone_number")
    private String telephoneNumber;
    @Column(name = "fax_number")
    private String faxNumber;
    private String email;
    @Column(name = "tax_identification_number")
    private String taxIdentificationNumber;
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityData cityData;
    @ManyToOne
    @JoinColumn(name = "voivodeship_id", nullable = false)
    private VoivodeshipData voivodeshipData;
    @OneToMany(mappedBy = "developerData")
    private Set<InvestmentData> investments;

    public DeveloperData() {
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

    public String getAddressFlatNumber() {
        return addressFlatNumber;
    }

    public void setAddressFlatNumber(String addressFlatNumber) {
        this.addressFlatNumber = addressFlatNumber;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
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

    public Set<InvestmentData> getInvestments() {
        return investments;
    }

    public void setInvestments(Set<InvestmentData> investments) {
        this.investments = investments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeveloperData)) return false;
        DeveloperData that = (DeveloperData) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddressCountry(), that.getAddressCountry()) && Objects.equals(getAddressStreet(), that.getAddressStreet()) && Objects.equals(getAddressBuildingNumber(), that.getAddressBuildingNumber()) && Objects.equals(getAddressFlatNumber(), that.getAddressFlatNumber()) && Objects.equals(getAddressPostalCode(), that.getAddressPostalCode()) && Objects.equals(getTelephoneNumber(), that.getTelephoneNumber()) && Objects.equals(getFaxNumber(), that.getFaxNumber()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getTaxIdentificationNumber(), that.getTaxIdentificationNumber()) && Objects.equals(getCityData(), that.getCityData()) && Objects.equals(getVoivodeshipData(), that.getVoivodeshipData()) && Objects.equals(getInvestments(), that.getInvestments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddressCountry(), getAddressStreet(), getAddressBuildingNumber(), getAddressFlatNumber(), getAddressPostalCode(), getTelephoneNumber(), getFaxNumber(), getEmail(), getTaxIdentificationNumber(), getCityData(), getVoivodeshipData(), getInvestments());
    }
}
