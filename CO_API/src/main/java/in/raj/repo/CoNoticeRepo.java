package in.raj.repo;

import in.raj.entities.CoNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoNoticeRepo extends JpaRepository<CoNoticeEntity,Integer> {

    public List<CoNoticeEntity> findByNoticeStatus(String status);
}
