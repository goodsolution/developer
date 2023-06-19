package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.CityData;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityData, Long> {

    @Query("SELECT DISTINCT c.name FROM CityData c JOIN InvestmentData  i ON i.cityData.id = c.id JOIN DeveloperData d ON d.id = i.developerData.id WHERE d.id = :developerId")
    public List<String> getCitiesByDeveloperId(Long developerId);

}
