package in.raj.repo;

import in.raj.entities.KidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KidRepo extends JpaRepository<KidEntity,Integer> {
    @Query("from KidEntity where caseNum =: caseNum")
    public List<KidEntity> findByCaseNum(Long caseNum);
}
