package in.raj.repo;

import in.raj.entities.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepo extends JpaRepository<EducationEntity,Integer> {
}
