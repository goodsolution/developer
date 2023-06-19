package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.InvestmentData;

@Repository
public interface InvestmentRepository extends JpaRepository<InvestmentData, Long> {


}