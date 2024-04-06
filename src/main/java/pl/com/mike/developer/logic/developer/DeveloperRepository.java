package pl.com.mike.developer.logic.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.Developer;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> getDeveloperDataByCode(String code);

}
