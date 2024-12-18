package gmky.core.service;

import gmky.core.api.model.CreateRoleRequest;
import gmky.core.api.model.FilterRoleResponse;
import gmky.core.api.model.UpdateRoleRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.RoleDto;
import gmky.core.enumeration.RoleTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    FilterRoleResponse filter(String name, List<RoleTypeEnum> type, Boolean isEnable, Boolean isDefault, Pageable pageable);

    RoleDto findById(Long id);

    RoleDto updateById(Long roleId, UpdateRoleRequest request);

    void deleteById(Long roleId);

    Page<ProfileDto> usersInRoleByRoleId(Long roleId, Pageable pageable);

    RoleDto createRole(CreateRoleRequest request);
}
