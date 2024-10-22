package in.raj.repo;

import in.raj.entities.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationRepo extends JpaRepository<EducationEntity,Integer> {
    @Query("from EducationEntity where caseNum=:caseNum")
    public EducationEntity findByCaseNum(Long caseNum);
}
