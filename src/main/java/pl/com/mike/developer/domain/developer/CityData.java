package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cities", schema = "course_platform")
public class CityData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "voivodeship_id")
    private Long voivodeshipId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityData)) return false;
        CityData cityData = (CityData) o;
        return Objects.equals(getId(), cityData.getId()) && Objects.equals(getName(), cityData.getName()) && Objects.equals(getVoivodeshipId(), cityData.getVoivodeshipId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getVoivodeshipId());
    }
}
