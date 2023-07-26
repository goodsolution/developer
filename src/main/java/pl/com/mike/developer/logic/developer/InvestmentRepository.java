package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.CityData;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentData, Long> {

    @Query("SELECT DISTINCT c FROM InvestmentData i JOIN CityData c ON i.cityId = c.id WHERE i.developerId = ?1")
    List<CityData> getInvestmentCitiesByDeveloperId(
            @Param("id") Long id
    );

}
