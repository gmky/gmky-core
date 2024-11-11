package gmky.core.repository;

import gmky.core.entity.Profile;
import gmky.core.enumeration.UserStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByUserId(String userId);

    boolean existsByUsername(String username);

    @Query(value = """
                SELECT p FROM Profile p WHERE (NULLIF(:username, '') IS NULL OR p.username LIKE :username%)
                    AND (NULLIF(:email, '') IS NULL OR p.email LIKE :email%)
                    AND (NULLIF(:fullName, '') IS NULL OR p.fullName LIKE :fullName% )
                    AND p.status IN :statuses
            """)
    Page<Profile> filterUsers(String username, String email, String fullName, List<UserStatusEnum> statuses, Pageable pageable);

    @Query(value = "SELECT profile FROM Profile profile JOIN RoleUser ru ON profile.userId = ru.userId JOIN ru.role r JOIN r.permissionSets ps JOIN ps.permissions p WHERE p.id = :permissionId")
    Page<Profile> usersHasPermissionByPermissionId(Long permissionId, Pageable pageable);

    @Query(value = "SELECT profile FROM Profile profile JOIN RoleUser ru ON profile.userId = ru.userId JOIN ru.role r WHERE r.id = :roleId")
    Page<Profile> usersInRoleByRoleId(Long roleId, Pageable pageable);

    @Query(value = "SELECT profile FROM Profile profile JOIN RoleUser ru ON profile.userId = ru.userId JOIN ru.role r JOIN r.permissionSets ps WHERE ps.id = :permissionSetId")
    Page<Profile> usersInPermissionSetByPermissionSetId(Long permissionSetId, Pageable pageable);

    @Query(value = "SELECT COUNT(1) FROM Profile profile JOIN RoleUser ru ON profile.userId = ru.userId JOIN ru.role r JOIN r.permissionSets ps WHERE ps.id = :permissionSetId")
    long countUsersInPermissionSetByPermissionSetId(Long permissionSetId);

    @Query(value = "SELECT COUNT(1) FROM Profile profile JOIN RoleUser ru ON profile.userId = ru.userId JOIN ru.role r WHERE r.id = :roleId")
    long countUsersInRoleByRoleId(Long roleId);
}