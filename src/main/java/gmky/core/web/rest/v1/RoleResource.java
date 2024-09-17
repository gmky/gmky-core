package gmky.core.web.rest.v1;

import gmky.core.aop.FeatureFlag;
import gmky.core.api.RoleClientApi;
import gmky.core.api.model.CreateRoleRequest;
import gmky.core.api.model.UpdateRoleRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.RoleDto;
import gmky.core.enumeration.RoleTypeEnum;
import gmky.core.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gmky.core.common.Constants.FF_AUTH_ROLE;
import static gmky.core.utils.ResponseUtil.data;
import static gmky.core.utils.ResponseUtil.noContent;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoleResource implements RoleClientApi {
    private final RoleService roleService;

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAuthority('role:create')")
    public ResponseEntity<RoleDto> createRole(CreateRoleRequest createRoleRequest) {
        var result = roleService.createRole(createRoleRequest);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<Void> deleteRoleById(Long id) {
        roleService.deleteById(id);
        return noContent();
    }

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAnyAuthority('role:view', 'role:edit', 'role:delete', 'role:approve')")
    public ResponseEntity<List<RoleDto>> filterRole(String name, RoleTypeEnum type, Boolean isEnable, Boolean isDefault, Pageable pageable) {
        var page = roleService.filter(name, type, isEnable, isDefault, pageable);
        return data(page);
    }

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAnyAuthority('role:view', 'role:edit', 'role:delete', 'role:approve')")
    public ResponseEntity<RoleDto> getRoleDetailById(Long id) {
        var result = roleService.findById(id);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAuthority('role:edit')")
    public ResponseEntity<RoleDto> updateRoleById(Long id, UpdateRoleRequest updateRoleRequest) {
        var result = roleService.updateById(id, updateRoleRequest);
        return data(result);
    }

    @Override
    @FeatureFlag(FF_AUTH_ROLE)
    @PreAuthorize("hasAuthority('profile:view') && hasAuthority('role:view')")
    public ResponseEntity<List<ProfileDto>> usersInRoleById(Long id, Pageable pageable) {
        var result = roleService.usersInRoleByRoleId(id, pageable);
        return data(result);
    }
}
