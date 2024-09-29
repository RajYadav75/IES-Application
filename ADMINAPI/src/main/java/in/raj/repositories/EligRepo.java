package in.raj.repositories;

import in.raj.entities.EligibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EligRepo extends JpaRepository<EligibilityEntity,Integer> {
}
