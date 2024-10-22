package in.raj.repo;

import in.raj.entities.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepo extends JpaRepository<IncomeEntity,Integer> {
}
