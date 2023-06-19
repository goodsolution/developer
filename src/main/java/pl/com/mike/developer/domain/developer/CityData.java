package pl.com.mike.developer.domain.developer;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cities")
public class CityData implements Serializable {
    @javax.persistence.Id
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "voivodeship_id")
    private Long voivodeshipId;

    @OneToMany(mappedBy = "cityData")
    private Set<DeveloperData> developers;

    @OneToMany(mappedBy = "cityData")
    private Set<InvestmentData> investments;

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

    public void setVoivodeshipId(Long voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    public Set<DeveloperData> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperData> developers) {
        this.developers = developers;
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
        if (!(o instanceof CityData)) return false;
        CityData cityData = (CityData) o;
        return Objects.equals(getId(), cityData.getId()) && Objects.equals(getName(), cityData.getName()) && Objects.equals(getVoivodeshipId(), cityData.getVoivodeshipId()) && Objects.equals(getDevelopers(), cityData.getDevelopers()) && Objects.equals(getInvestments(), cityData.getInvestments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getVoivodeshipId(), getDevelopers(), getInvestments());
    }
}
