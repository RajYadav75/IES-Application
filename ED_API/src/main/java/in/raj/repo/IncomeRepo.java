package in.raj.repo;

import in.raj.entities.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IncomeRepo extends JpaRepository<IncomeEntity,Integer> {
    @Query("from IncomeEntity where caseNum=:caseNum")
    public IncomeEntity findByCaseNum(Long caseNum);
}
