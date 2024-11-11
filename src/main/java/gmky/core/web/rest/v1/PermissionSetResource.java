package gmky.core.web.rest.v1;

import gmky.core.aop.FeatureFlag;
import gmky.core.api.PermissionSetClientApi;
import gmky.core.api.model.CreatePermissionSetRequest;
import gmky.core.api.model.FilterPermissionSetResponse;
import gmky.core.api.model.UpdatePermissionSetRequest;
import gmky.core.dto.PermissionDto;
import gmky.core.dto.PermissionSetDto;
import gmky.core.dto.ProfileDto;
import gmky.core.enumeration.PermissionSetTypeEnum;
import gmky.core.service.PermissionSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gmky.core.common.Constants.FF_AUTH_PERMISSION_SET;
import static gmky.core.utils.ResponseUtil.data;
import static gmky.core.utils.ResponseUtil.noContent;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PermissionSetResource implements PermissionSetClientApi {
    private final PermissionSetService permissionSetService;

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAuthority('permissionset:create')")
    public ResponseEntity<PermissionSetDto> createPermissionSet(CreatePermissionSetRequest createPermissionSetRequest) {
        var result = permissionSetService.create(createPermissionSetRequest);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAuthority('permissionset:delete')")
    public ResponseEntity<Void> deletePermissionSetById(Long id) {
        permissionSetService.deleteById(id);
        return noContent();
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAnyAuthority('permissionset:view', 'permissionset:edit', 'permissionset:delete', 'permissionset:approve')")
    public ResponseEntity<FilterPermissionSetResponse> filterPermissionSet(String name, List<PermissionSetTypeEnum> types, Pageable pageable) {
        var page = permissionSetService.filter(name, types, pageable);
        return data(page);
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAnyAuthority('permissionset:view')")
    public ResponseEntity<List<PermissionDto>> getAssignedPsById(Long id) {
        return data(permissionSetService.getAssignedPermissionById(id));
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAnyAuthority('permissionset:view', 'permissionset:edit', 'permissionset:delete', 'permissionset:approve')")
    public ResponseEntity<PermissionSetDto> getPermissionSetDetailById(Long id) {
        var result = permissionSetService.findById(id);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAuthority('permissionset:edit')")
    public ResponseEntity<PermissionSetDto> updatePermissionSetById(Long id, UpdatePermissionSetRequest updatePermissionSetRequest) {
        var result = permissionSetService.updateById(id, updatePermissionSetRequest);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_PERMISSION_SET)
    @PreAuthorize("hasAuthority('profile:view') && hasAuthority('permissionset:view')")
    public ResponseEntity<List<ProfileDto>> usersInPermissionSetById(Long id, Pageable pageable) {
        var page = permissionSetService.usersInPermissionSetByPermissionSetId(id, pageable);
        return data(page);
    }
}
