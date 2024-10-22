package in.raj.repo;

import in.raj.entities.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlanRepo extends JpaRepository<PlanEntity,Integer> {
    @Query("update PlanEntity set activeSw=:status where planId=:userId")
    public Integer changePlanStatus(Integer userId, String status);
}
