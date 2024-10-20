package in.raj.repositories;

import in.raj.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//2
public interface AppRepo extends JpaRepository<AppEntity,Long> {

    public List<AppEntity> fetchUserApps();
    @Query(value = "from AppEntity where userId =:userId")
    public List<AppEntity> fetchCwApps(Integer userId);
}
