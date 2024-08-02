package gmky.core.service.impl;

import gmky.core.api.model.SummaryItemDto;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.entity.Permission;
import gmky.core.entity.RoleUser;
import gmky.core.enumeration.UserStatusEnum;
import gmky.core.exception.NotFoundException;
import gmky.core.exception.UnauthorizedException;
import gmky.core.mapper.ProfileMapper;
import gmky.core.repository.PermissionRepository;
import gmky.core.repository.ProfileRepository;
import gmky.core.repository.RoleRepository;
import gmky.core.repository.RoleUserRepository;
import gmky.core.service.UserService;
import gmky.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static gmky.core.exception.CoreExceptionEnum.PROFILE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ProfileRepository profileRepository;
    private final PermissionRepository permissionRepository;
    private final ProfileMapper profileMapper;
    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public ProfileDto getCurrentUserProfile() {
        var username = SecurityUtil.getCurrentUsername();
        if (username == null) throw new UnauthorizedException();
        var profile = profileRepository.findByUsername(username).orElse(null);
        if (profile == null) throw new UnauthorizedException();
        return profileMapper.toDto(profile);
    }

    @Override
    public List<SummaryItemDto> getCurrentUserSummary() {
        var userId = SecurityUtil.getCurrentUserId();
        if (userId == null) throw new UnauthorizedException();
        return permissionRepository.getPermissionsByUserId(userId).stream()
                .collect(Collectors.groupingBy(Permission::getResourceCode, Collectors.mapping(Permission::getPermissionCode, Collectors.toList())))
                .entrySet().stream().map(entry -> {
                    var item = new SummaryItemDto();
                    item.setResource(entry.getKey());
                    item.setPermissions(entry.getValue());
                    return item;
                }).toList();
    }

    @Override
    public ProfileDto getProfileByUserId(String userId) {
        var profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile == null) throw new NotFoundException(PROFILE_NOT_FOUND);
        return profileMapper.toDto(profile);
    }

    @Override
    public ProfileDto updateByUserId(String userId, UpdateUserRequest request) {
        var profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile == null) throw new NotFoundException(PROFILE_NOT_FOUND);
        profile = profileMapper.partialUpdate(request, profile);
        removeRoles(request.getRemovedRoles(), userId);
        addRoles(request.getAddedRoles(), userId);
        return profileMapper.toDto(profileRepository.save(profile));
    }

    @Override
    public Page<ProfileDto> filterUsers(String username, String email, String fullName, List<UserStatusEnum> statuses, Pageable pageable) {
        var page = profileRepository.filterUsers(username, email, fullName, statuses, pageable);
        return page.map(profileMapper::toDto);
    }

    private void removeRoles(List<Long> roleIdList, String userId) {
        if (CollectionUtils.isEmpty(roleIdList)) return;
        roleUserRepository.deleteByUserIdAndRoleIdIn(userId, roleIdList);
    }

    private void addRoles(List<Long> roleIdList, String userId) {
        if (CollectionUtils.isEmpty(roleIdList)) return;
        var roleUserList = roleIdList.stream().map(roleId -> {
            var existedRole = roleRepository.findById(roleId).orElse(null);
            if (existedRole == null) return null;
            var roleUser = new RoleUser();
            roleUser.setUserId(userId);
            roleUser.setRole(existedRole);
            return roleUser;
        }).filter(Objects::nonNull).toList();
        roleUserRepository.saveAll(roleUserList);
    }
}
