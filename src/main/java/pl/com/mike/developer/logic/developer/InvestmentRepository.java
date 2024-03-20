package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentData, Long> {

    @Query("SELECT DISTINCT c.name FROM InvestmentData i JOIN i.investmentCity c WHERE i.developer.code = :code")
    List<String> getInvestmentCitiesByDeveloperCode(@Param("code") String code);

    @Query("SELECT DISTINCT i FROM InvestmentData i WHERE i.developer.code = :code")
    List<InvestmentData> getInvestmentsByDeveloperCode(@Param("code") String code);

    @Query("SELECT DISTINCT b.investmentBuildings FROM BuildingData b JOIN b.premises p WHERE p.id = :premiseId")
    List<InvestmentData> getInvestmentsByPremiseId(@Param("premiseId") Long premiseId);

}