package pl.com.mike.developer.domain.developer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "voivodeships", schema = "course_platform")
public class VoivodeshipData {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "create_time")
    private LocalDateTime createdAt;
    @Column(name = "edit_time")
    private LocalDateTime updatedAt;
    @Column(name = "delete_time")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "voivodeship")
    private Set<CityData> cities;

    public VoivodeshipData() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
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
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}