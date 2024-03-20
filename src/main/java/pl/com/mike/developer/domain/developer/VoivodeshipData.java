package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "voivodeships", schema = "course_platform")
public class VoivodeshipData {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "voivodeship")
    private Set<CityData> cities;

    public VoivodeshipData() {
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

    public Set<CityData> getCities() {
        return cities;
    }

    public void setCities(Set<CityData> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoivodeshipData)) return false;
        VoivodeshipData that = (VoivodeshipData) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getCities(), that.getCities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCities());
    }
}
