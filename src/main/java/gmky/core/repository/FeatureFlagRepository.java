package gmky.core.repository;

import gmky.core.entity.FeatureFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {
    Optional<FeatureFlagEntity> findByKey(String key);
}
