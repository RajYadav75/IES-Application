package in.raj.repositories;

import in.raj.entities.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepo extends JpaRepository<PlanEntity,Integer> {
}
