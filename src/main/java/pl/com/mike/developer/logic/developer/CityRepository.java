package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.CityData;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityData, Long> {

    @Query("SELECT DISTINCT i.investmentCity FROM InvestmentData i WHERE i.developer.code = :code")
    List<CityData> getCitiesByDeveloperCode(@Param("code") String code);

}
