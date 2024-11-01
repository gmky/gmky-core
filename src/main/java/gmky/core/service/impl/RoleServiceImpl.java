package gmky.core.service.impl;

import gmky.core.api.model.CreateRoleRequest;
import gmky.core.api.model.UpdateRoleRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.RoleDto;
import gmky.core.enumeration.RoleTypeEnum;
import gmky.core.exception.BadRequestException;
import gmky.core.exception.ForbiddenException;
import gmky.core.exception.NotFoundException;
import gmky.core.mapper.ProfileMapper;
import gmky.core.mapper.RoleMapper;
import gmky.core.repository.PermissionSetRepository;
import gmky.core.repository.ProfileRepository;
import gmky.core.repository.RoleRepository;
import gmky.core.service.RoleService;
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
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionSetRepository permissionSetRepository;
    private final RoleMapper roleMapper;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public Page<RoleDto> filter(String name, List<RoleTypeEnum> type, Boolean isEnable, Boolean isDefault, Pageable pageable) {
        var page = roleRepository.filter(name, type, isEnable, isDefault, pageable);
        return page.map(roleMapper::toDto);
    }

    @Override
    public RoleDto findById(Long id) {
        var role = roleRepository.findById(id).orElse(null);
        if (role == null) throw new NotFoundException(ROLE_NOT_FOUND);
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional
    public RoleDto updateById(Long roleId, UpdateRoleRequest request) {
        var role = roleRepository.findById(roleId).orElse(null);
        if (role == null) throw new NotFoundException(ROLE_NOT_FOUND);
        var existed = roleRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), roleId);
        if (existed) throw new BadRequestException(ROLE_NAME_EXISTED);
        if (RoleTypeEnum.TEMPLATE == role.getType()) {
            throw new ForbiddenException();
        }
        role = roleMapper.partialUpdate(request, role);
        role = roleRepository.save(role);
        addPermissionSet(roleId, request.getAddedPermissionSet());
        removePermissionSet(roleId, request.getRemovedPermissionSet());
        return roleMapper.toDto(role);
    }

    @Override
    public void deleteById(Long roleId) {
        var role = roleRepository.findById(roleId).orElse(null);
        if (role == null) throw new NotFoundException(ROLE_NOT_FOUND);
        if (RoleTypeEnum.TEMPLATE == role.getType()) {
            throw new ForbiddenException();
        }
        var countUsers = profileRepository.countUsersInRoleByRoleId(roleId);
        if (countUsers <= 0) throw new BadRequestException(ROLE_NOT_EMPTY);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Page<ProfileDto> usersInRoleByRoleId(Long roleId, Pageable pageable) {
        var page = profileRepository.usersInRoleByRoleId(roleId, pageable);
        return page.map(profileMapper::toDto);
    }

    @Override
    @Transactional
    public RoleDto createRole(CreateRoleRequest request) {
        var existed = roleRepository.existsByNameIgnoreCase(request.getName());
        if (existed) throw new BadRequestException(ROLE_NAME_EXISTED);

        var role = roleMapper.toEntity(request);
        role.setType(RoleTypeEnum.CUSTOM);
        role = roleRepository.save(role);

        addPermissionSet(role.getId(), request.getPsIdList());

        return roleMapper.toDto(role);
    }

    private void removePermissionSet(Long roleId, List<Long> psIdList) {
        if (CollectionUtils.isEmpty(psIdList)) return;
        log.info("Start to remove permission set: {}", psIdList);
        roleRepository.deletePermissionSetFromRole(roleId, psIdList);
    }

    private void addPermissionSet(Long roleId, List<Long> psIdList) {
        if (CollectionUtils.isEmpty(psIdList)) return;
        log.info("Start to add permission set: {}", psIdList);
        var psList = permissionSetRepository.findAllById(psIdList);
        if (psList.size() != psIdList.size()) throw new BadRequestException(INVALID_PERMISSION_SET);
        psIdList.forEach(psId -> roleRepository.addPermissionSetToRole(psId, roleId));
    }
}
