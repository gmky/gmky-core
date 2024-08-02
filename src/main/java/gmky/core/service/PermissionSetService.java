package gmky.core.service;

import gmky.core.api.model.CreatePermissionSetRequest;
import gmky.core.api.model.UpdatePermissionSetRequest;
import gmky.core.dto.PermissionSetDto;
import gmky.core.dto.ProfileDto;
import gmky.core.enumeration.PermissionSetTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionSetService {
    Page<PermissionSetDto> filter(String name, PermissionSetTypeEnum type, Pageable pageable);

    PermissionSetDto findById(Long id);

    PermissionSetDto updateById(Long psId, UpdatePermissionSetRequest request);

    void deleteById(Long psId);

    Page<ProfileDto> usersInPermissionSetByPermissionSetId(Long permissionSetId, Pageable pageable);

    PermissionSetDto create(CreatePermissionSetRequest request);
}
