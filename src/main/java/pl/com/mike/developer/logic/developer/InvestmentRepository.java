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

    @Query(nativeQuery = true, value = "select distinct c.name from investments join cities c on c.id = investments.city_id where developer_id = (select developers.id from developers where developers.code = :code)")
    List<String> getInvestmentCitiesByDeveloperCode(@Param("code") String code);

    @Query(nativeQuery = true, value = "select distinct investments.name, investments.address_street, investments.description, investments.city_id from investments where developer_id = (select developers.id from developers where developers.code = :code)")
    List<InvestmentData> getInvestmentsByDeveloperCode(@Param("code") String code);

}
