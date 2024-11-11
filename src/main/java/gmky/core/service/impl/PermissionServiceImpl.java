package gmky.core.service.impl;

import gmky.core.api.model.FilterPermissionResponse;
import gmky.core.dto.PermissionDto;
import gmky.core.dto.ProfileDto;
import gmky.core.exception.NotFoundException;
import gmky.core.mapper.PermissionMapper;
import gmky.core.mapper.ProfileMapper;
import gmky.core.repository.PermissionRepository;
import gmky.core.repository.ProfileRepository;
import gmky.core.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static gmky.core.exception.CoreExceptionEnum.PERMISSION_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public FilterPermissionResponse filter(String permissionCode, String resourceCode, Pageable pageable) {
        var page = permissionRepository.filter(permissionCode, resourceCode, pageable);
        return permissionMapper.toResponse(page);
    }

    @Override
    public PermissionDto findById(Long id) {
        var permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) throw new NotFoundException(PERMISSION_NOT_FOUND);
        return permissionMapper.toDto(permission);
    }

    @Override
    public Page<ProfileDto> usersHasPermissionById(Long permissionId, Pageable pageable) {
        var page = profileRepository.usersHasPermissionByPermissionId(permissionId, pageable);
        return page.map(profileMapper::toDto);
    }
}
