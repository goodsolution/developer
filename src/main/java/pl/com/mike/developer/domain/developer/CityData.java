package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cities", schema = "course_platform")
public class CityData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "voivodeship_id")
    private Long voivodeshipId;
    @ManyToOne
    @JoinColumn(name = "voivodeship_id", insertable = false, updatable = false)
    private VoivodeshipData voivodeship;
    @OneToMany(mappedBy = "investmentCity")
    private Set<InvestmentData> investments;
    @OneToMany(mappedBy = "developerCity")
    private Set<DeveloperData> developers;
    @OneToMany(mappedBy = "cityBuildings")
    private Set<BuildingData> buildings;

    public CityData() {
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

    public Long getVoivodeshipId() {
        return voivodeshipId;
    }

    public VoivodeshipData getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeshipId(Long voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    public void setVoivodeship(VoivodeshipData voivodeship) {
        this.voivodeship = voivodeship;
    }

    public Set<InvestmentData> getInvestments() {
        return investments;
    }

    public void setInvestments(Set<InvestmentData> investments) {
        this.investments = investments;
    }

    public Set<DeveloperData> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperData> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityData)) return false;
        CityData cityData = (CityData) o;
        return Objects.equals(getId(), cityData.getId()) && Objects.equals(getName(), cityData.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

}
