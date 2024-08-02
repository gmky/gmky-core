package gmky.core.repository;

import gmky.core.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
                SELECT p FROM Permission p
                    WHERE (NULLIF(:permissionCode, '') IS NULL OR p.permissionCode LIKE :permissionCode%)
                        AND (NULLIF(:resourceCode, '') IS NULL OR p.resourceCode LIKE :resourceCode% )
            """)
    Page<Permission> filter(String permissionCode, String resourceCode, Pageable pageable);

    @Query("""
                SELECT p FROM Permission p JOIN p.permissionSets ps JOIN ps.roles r JOIN RoleUser ru ON ru.role.id = r.id WHERE ru.userId = :userId
            """)
    List<Permission> getPermissionsByUserId(String userId);
}