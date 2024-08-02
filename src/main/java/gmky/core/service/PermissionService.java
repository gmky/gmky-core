package gmky.core.service;

import gmky.core.dto.PermissionDto;
import gmky.core.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    Page<PermissionDto> filter(String permissionCode, String resourceCode, Pageable pageable);

    PermissionDto findById(Long id);

    Page<ProfileDto> usersHasPermissionById(Long permissionId, Pageable pageable);
}
