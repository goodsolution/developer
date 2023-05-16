package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.InvestmentData;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentData, Long> {
    @Query("SELECT i FROM InvestmentData i WHERE i.developerData.id = ?1")
    List<Optional<InvestmentData>> findInvestmentDataByDeveloperId(Long developerId);


}
