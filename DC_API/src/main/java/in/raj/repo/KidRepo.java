package in.raj.repo;

import in.raj.entities.KidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepo extends JpaRepository<KidEntity,Integer> {
}
