package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.Investment;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    @Query("SELECT c.name FROM Investment i JOIN i.investmentCity c WHERE i.developer.code = :code")
    List<String> getInvestmentCitiesByDeveloperCode(@Param("code") String code);

    @Query("SELECT i FROM Investment i WHERE i.developer.code = :code")
    List<Investment> getInvestmentsByDeveloperCode(@Param("code") String code);

    @Query("SELECT b.investmentBuildings FROM Building b JOIN b.premises p WHERE p.id = :premiseId")
    List<Investment> getInvestmentsByPremiseId(@Param("premiseId") Long premiseId);

}