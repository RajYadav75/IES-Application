package in.raj.repo;

import in.raj.entities.PlanSelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlanSelRepo extends JpaRepository<PlanSelEntity,Integer> {
    @Query("from PlanSelEntity where caseNum=: caseNum")
    public PlanSelEntity findByCaseNum(Long caseNum);
}
