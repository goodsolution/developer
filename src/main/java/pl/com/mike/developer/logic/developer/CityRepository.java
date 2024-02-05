package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.CityData;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityData, Long> {


    @Query(nativeQuery = true, value = "select distinct c.id, c.name, c.voivodeship_id from cities c join investments i on c.id = i.city_id where i.developer_id = (select developers.id from developers where developers.code = :code)")
    List<CityData> getCitiesByDeveloperCode(@Param("code") String code);

}
