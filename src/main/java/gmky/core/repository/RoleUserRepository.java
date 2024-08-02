package gmky.core.repository;

import gmky.core.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long>, JpaSpecificationExecutor<RoleUser> {
    void deleteByUserIdAndRoleIdIn(String userId, List<Long> roleIdList);
}