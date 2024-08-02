package gmky.core.repository;

import gmky.core.entity.PermissionSet;
import gmky.core.enumeration.PermissionSetTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionSetRepository extends JpaRepository<PermissionSet, Long>, JpaSpecificationExecutor<PermissionSet> {
    @Query(value = """
                SELECT ps FROM PermissionSet ps WHERE (NULLIF(:name, '') IS NULL OR ps.name LIKE :name%)
                    AND (:type IS NULL OR ps.type = :type)
            """)
    Page<PermissionSet> filter(String name, PermissionSetTypeEnum type, Pageable pageable);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM ps_item psi WHERE psi.ps_id = :psId AND psi.permission_id IN :permissionIds")
    void deletePermissionFromPermissionSet(Long psId, List<Long> permissionIds);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ps_item(permission_id, ps_id) VALUE (:permissionId, :psId)")
    void addPermissionToPermissionSet(Long permissionId, Long psId);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}