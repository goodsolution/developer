package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.AvailablePremiseByCityGetResponse;
import pl.com.mike.developer.domain.developer.DeveloperData;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperData, Long> {
    @Query("SELECT new pl.com.mike.developer.AvailablePremiseByCityGetResponse(c.name, COUNT(p.id)) FROM DeveloperData d " +
            "JOIN InvestmentData i ON d.id = i.developerData.id " +
            "JOIN BuildingData b ON i.id = b.investmentData.id " +
            "JOIN PremiseData p ON b.id = p.buildingData.id " +
            "JOIN CityData c ON i.cityData.id = c.id " +
            "WHERE p.salesStatus = 'a' AND d.id = :developerId " +
            "GROUP BY c.id")
    List<AvailablePremiseByCityGetResponse> getAvailablePremisesByCity(@Param("developerId") Long developerId);


}
