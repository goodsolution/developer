package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.BuildingData;
@Repository
public interface BuildingRepository extends JpaRepository<BuildingData, Long> {

}
