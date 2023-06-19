package pl.com.mike.developer.domain.developer;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "voivodeships")
public class VoivodeshipData implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "voivodeshipData")
    private Set<DeveloperData> developers;

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

    public Set<DeveloperData> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperData> developers) {
        this.developers = developers;
    }
}
