package in.raj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface CoTriggerRepo extends JpaRepository<CoTriggerEntity, Serializable> {
    public List<CoTriggerEntity> findByTrgStatus(String status);
    public CoTriggerEntity findByCaseNum(Long caseNum);
}
