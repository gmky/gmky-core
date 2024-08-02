package gmky.core.web.rest.v1;

import gmky.core.api.PermissionClientApi;
import gmky.core.dto.PermissionDto;
import gmky.core.dto.ProfileDto;
import gmky.core.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gmky.core.utils.ResponseUtil.data;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PermissionResource implements PermissionClientApi {
    private final PermissionService permissionService;

    @Override
    @PreAuthorize("hasAnyAuthority('permission:view', 'permission:edit', 'permission:approve', 'permission:delete')")
    public ResponseEntity<List<PermissionDto>> filterPermission(String permissionCode, String resourceCode, Pageable pageable) {
        var page = permissionService.filter(permissionCode, resourceCode, pageable);
        return data(page);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('permission:view', 'permission:edit', 'permission:approve', 'permission:delete')")
    public ResponseEntity<PermissionDto> findById(Long id) {
        var permission = permissionService.findById(id);
        return data(permission);
    }

    @Override
    @PreAuthorize("hasAuthority('profile:view') && hasAuthority('permission:view')")
    public ResponseEntity<List<ProfileDto>> usersHasPermissionById(Long id, Pageable pageable) {
        var page = permissionService.usersHasPermissionById(id, pageable);
        return data(page);
    }
}