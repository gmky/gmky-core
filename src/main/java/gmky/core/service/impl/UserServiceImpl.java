package gmky.core.service.impl;

import gmky.core.api.model.CreateUserRequest;
import gmky.core.api.model.FilterUserResponse;
import gmky.core.api.model.SummaryItemDto;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.RoleDto;
import gmky.core.entity.Permission;
import gmky.core.entity.Profile;
import gmky.core.entity.RoleUser;
import gmky.core.enumeration.UserStatusEnum;
import gmky.core.exception.BadRequestException;
import gmky.core.exception.InternalServerException;
import gmky.core.exception.NotFoundException;
import gmky.core.exception.UnauthorizedException;
import gmky.core.keycloak.api.KeycloakAdminClientApi;
import gmky.core.keycloak.api.model.KeycloakCredentialInfo;
import gmky.core.keycloak.api.model.UserRepresentation;
import gmky.core.mapper.ProfileMapper;
import gmky.core.mapper.RoleMapper;
import gmky.core.repository.PermissionRepository;
import gmky.core.repository.ProfileRepository;
import gmky.core.repository.RoleRepository;
import gmky.core.repository.RoleUserRepository;
import gmky.core.service.UserService;
import gmky.core.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static gmky.core.exception.CoreExceptionEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String KEYCLOAK_CREDENTIAL_TYPE = "password";

    private final ProfileRepository profileRepository;
    private final PermissionRepository permissionRepository;
    private final ProfileMapper profileMapper;
    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;
    private final KeycloakAdminClientApi keycloakAdminClientApi;
    private final RoleMapper roleMapper;

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
        if (userId == null) return List.of();
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
    @Transactional
    public ProfileDto updateByUserId(String userId, UpdateUserRequest request) {
        var profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile == null) throw new NotFoundException(PROFILE_NOT_FOUND);
        profile = profileMapper.partialUpdate(request, profile);
        setLockInfo(profile, request.getStatus());
        removeRoles(request.getRemovedRoles(), userId);
        addRoles(request.getAddedRoles(), userId);
        var keycloakReq = new UserRepresentation();
        if (request.getStatus() != profile.getStatus()) {
            keycloakReq.setEnabled(request.getStatus() == UserStatusEnum.ACTIVE);
            keycloakAdminClientApi.updateUserByUserId(userId, keycloakReq);
        }
        return profileMapper.toDto(profileRepository.save(profile));
    }

    @Override
    public FilterUserResponse filterUsers(String username, String email, String fullName, List<UserStatusEnum> statuses, Pageable pageable) {
        var page = profileRepository.filterUsers(username, email, fullName, statuses, pageable);
        return profileMapper.toResponse(page);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest request) {
        var existed = profileRepository.existsByUsername(request.getUsername());
        if (existed) throw new BadRequestException(USERNAME_EXISTED);

        var keycloakRequest = profileMapper.toKeycloakRequest(request);
        keycloakRequest.setEnabled(true);
        keycloakRequest.setEmailVerified(request.getEmailVerified());
        var credential = new KeycloakCredentialInfo();
        credential.setTemporary(request.getIsTemporaryPassword());
        credential.setType(KEYCLOAK_CREDENTIAL_TYPE);
        credential.setValue(request.getPassword());
        keycloakRequest.setCredentials(List.of(credential));

        var profile = profileMapper.toEntity(request);
        profile.setFullName(String.format("%s %s", keycloakRequest.getFirstName(), keycloakRequest.getLastName()));
        try {
            keycloakAdminClientApi.createUser(keycloakRequest);
            var user = keycloakAdminClientApi.getUserByUsername(profile.getUsername()).get(0);
            profile.setUserId(user.getId());
            profileRepository.save(profile);
            addRoles(request.getRoles(), user.getId());
        } catch (Exception e) {
            log.error("[createUser][{}] Failed to create user", request.getUsername(), e);
            throw new InternalServerException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void logout(String userId) {
        try {
            keycloakAdminClientApi.logout(userId);
        } catch (Exception e) {
            log.error("[logout][{}] Failed to log out user", userId, e);
            throw new InternalServerException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RoleDto> getAssignedRoleByUserId(String userId) {
        var result = roleRepository.getAssignedRoleByUserId(userId);
        return roleMapper.toDtoList(result);
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

    private void setLockInfo(Profile profile, UserStatusEnum status) {
        if (status == null) return;
        switch (status) {
            case LOCKED -> profile.setLockedAt(Instant.now());
            case ACTIVE, PENDING -> profile.setLockedAt(null);
        }
    }
}
