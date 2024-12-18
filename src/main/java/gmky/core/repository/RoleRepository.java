package gmky.core.repository;

import gmky.core.entity.Role;
import gmky.core.enumeration.RoleTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    @Query("""
                SELECT r FROM Role r
                    WHERE r.type IN :types
                    AND (NULLIF(:name, '') IS NULL OR r.name LIKE :name% )
                    AND (:isEnable IS NULL OR r.isEnable = :isEnable)
                    AND (:isDefault IS NULL OR r.isDefault = :isDefault)
            """)
    Page<Role> filter(String name, List<RoleTypeEnum> types, Boolean isEnable, Boolean isDefault, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM ps_role psr WHERE psr.role_id = :roleId AND psr.ps_id IN :psIdList")
    void deletePermissionSetFromRole(Long roleId, List<Long> psIdList);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ps_role(ps_id, role_id) VALUES (:psId, :roleId)")
    void addPermissionSetToRole(Long psId, Long roleId);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("""
        SELECT r FROM Role r LEFT JOIN RoleUser ru ON ru.role.id = r.id WHERE ru.userId = :userId
    """)
    List<Role> getAssignedRoleByUserId(String userId);
}