package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.AggregatedValues;
import pl.com.mike.developer.domain.developer.PremiseData;

@Repository
public interface PremiseRepository extends JpaRepository<PremiseData, Long> {

    @Query(nativeQuery = true, value = "select * from premises join buildings on premises.building_id = buildings.id where buildings.investment_id = :id")
    Iterable<PremiseData> findAllByInvestmentId(@Param("id") Long id);

    @Query("SELECT MIN(p.totalPrice) AS minPrice, MAX(p.totalPrice) AS maxPrice " +
            "FROM PremiseData p JOIN p.building b " +
            "WHERE b.investmentId = :investmentId")
    AggregatedValues findPremisePriceRangeByInvestmentId(@Param("investmentId") Long investmentId);


}
