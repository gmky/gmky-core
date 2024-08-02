package gmky.core.service.impl;

import gmky.core.api.model.CreatePermissionSetRequest;
import gmky.core.api.model.UpdatePermissionSetRequest;
import gmky.core.dto.PermissionSetDto;
import gmky.core.dto.ProfileDto;
import gmky.core.enumeration.PermissionSetTypeEnum;
import gmky.core.exception.BadRequestException;
import gmky.core.exception.ForbiddenException;
import gmky.core.exception.NotFoundException;
import gmky.core.mapper.PermissionSetMapper;
import gmky.core.mapper.ProfileMapper;
import gmky.core.repository.PermissionRepository;
import gmky.core.repository.PermissionSetRepository;
import gmky.core.repository.ProfileRepository;
import gmky.core.service.PermissionSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static gmky.core.exception.CoreExceptionEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionSetServiceImpl implements PermissionSetService {
    private final PermissionSetRepository permissionSetRepository;
    private final PermissionSetMapper permissionSetMapper;
    private final PermissionRepository permissionRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public Page<PermissionSetDto> filter(String name, PermissionSetTypeEnum type, Pageable pageable) {
        var page = permissionSetRepository.filter(name, type, pageable);
        return page.map(permissionSetMapper::toDto);
    }

    @Override
    public PermissionSetDto findById(Long id) {
        var permissionSet = permissionSetRepository.findById(id).orElse(null);
        if (permissionSet == null) throw new NotFoundException(PERMISSION_SET_NOT_FOUND);
        return permissionSetMapper.toDto(permissionSet);
    }

    @Override
    @Transactional
    public PermissionSetDto updateById(Long psId, UpdatePermissionSetRequest request) {
        var permissionSet = permissionSetRepository.findById(psId).orElse(null);
        if (permissionSet == null) throw new NotFoundException(PERMISSION_SET_NOT_FOUND);

        var existed = permissionSetRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), psId);
        if (existed) throw new BadRequestException(PERMISSION_SET_NAME_EXISTED);

        if (PermissionSetTypeEnum.TEMPLATE == permissionSet.getType()) {
            throw new ForbiddenException();
        }
        permissionSet = permissionSetMapper.partialUpdate(request, permissionSet);
        permissionSet = permissionSetRepository.save(permissionSet);
        addPermission(psId, request.getAddedPermission());
        removePermission(psId, request.getRemovedPermission());
        return permissionSetMapper.toDto(permissionSet);
    }

    @Override
    public void deleteById(Long psId) {
        var permissionSet = permissionSetRepository.findById(psId).orElse(null);
        if (permissionSet == null) throw new NotFoundException(PERMISSION_SET_NOT_FOUND);
        if (PermissionSetTypeEnum.TEMPLATE == permissionSet.getType()) {
            throw new ForbiddenException();
        }
        var usersCount = profileRepository.countUsersInPermissionSetByPermissionSetId(psId);
        if (usersCount <= 0) throw new ForbiddenException(PERMISSION_SET_NOT_EMPTY);
        permissionSetRepository.deleteById(psId);
    }

    @Override
    public Page<ProfileDto> usersInPermissionSetByPermissionSetId(Long permissionSetId, Pageable pageable) {
        var page = profileRepository.usersInPermissionSetByPermissionSetId(permissionSetId, pageable);
        return page.map(profileMapper::toDto);
    }

    @Override
    public PermissionSetDto create(CreatePermissionSetRequest request) {
        var existed = permissionSetRepository.existsByNameIgnoreCase(request.getName());
        if (existed) throw new BadRequestException(PERMISSION_SET_NAME_EXISTED);

        var permissionSet = permissionSetMapper.toEntity(request);
        permissionSet = permissionSetRepository.save(permissionSet);

        addPermission(permissionSet.getId(), request.getPermissionIds());

        return permissionSetMapper.toDto(permissionSet);
    }

    private void removePermission(Long psId, List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) return;
        log.info("Start to remove permission ids: {}", permissionIds);
        permissionSetRepository.deletePermissionFromPermissionSet(psId, permissionIds);
    }

    private void addPermission(Long psId, List<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) return;
        log.info("Start to add permission ids: {}", permissionIds);
        var permissions = permissionRepository.findAllById(permissionIds);
        if (permissions.size() != permissionIds.size()) throw new NotFoundException(PERMISSION_SET_NOT_FOUND);
        permissionIds.forEach(item -> permissionSetRepository.addPermissionToPermissionSet(item, psId));
    }
}
