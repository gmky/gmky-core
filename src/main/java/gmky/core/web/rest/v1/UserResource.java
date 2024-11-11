package gmky.core.web.rest.v1;

import gmky.core.aop.FeatureFlag;
import gmky.core.api.UserClientApi;
import gmky.core.api.model.CreateUserRequest;
import gmky.core.api.model.FilterUserResponse;
import gmky.core.api.model.SummaryItemDto;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.RoleDto;
import gmky.core.enumeration.UserStatusEnum;
import gmky.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gmky.core.common.Constants.FF_AUTH_PROFILE;
import static gmky.core.utils.ResponseUtil.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserResource implements UserClientApi {
    private final UserService userService;

    @Override
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<Void> createUser(CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return created();
    }

    @Override
    @FeatureFlag(FF_AUTH_PROFILE)
    @PreAuthorize("hasAnyAuthority('user:view')")
    public ResponseEntity<FilterUserResponse> filterUsers(String username, String email, String fullName, List<UserStatusEnum> status, Pageable pageable) {
        var page = userService.filterUsers(username, email, fullName, status, pageable);
        return data(page);
    }

    @Override
    @FeatureFlag(FF_AUTH_PROFILE)
    @PreAuthorize("hasAnyAuthority('profile:view', 'profile:edit', 'profile:delete', 'profile:approve')")
    public ResponseEntity<ProfileDto> getUserDetailById(String userId) {
        var result = userService.getProfileByUserId(userId);
        return data(result);
    }

    @Override
    @PreAuthorize("hasAuthority('user:view')")
    public ResponseEntity<List<RoleDto>> getUserRole(String userId) {
        return data(userService.getAssignedRoleByUserId(userId));
    }

    @Override
    @PreAuthorize("hasAuthority('user:edit')")
    public ResponseEntity<Void> logoutAllByUserId(String userId) {
        userService.logout(userId);
        return noContent();
    }

    @Override
    @FeatureFlag(FF_AUTH_PROFILE)
    public ResponseEntity<ProfileDto> profile() {
        var profile = userService.getCurrentUserProfile();
        return data(profile);
    }

    @Override
    @FeatureFlag(FF_AUTH_PROFILE)
    public ResponseEntity<List<SummaryItemDto>> summary() {
        var summary = userService.getCurrentUserSummary();
        return data(summary);
    }

    @Override
    @FeatureFlag(FF_AUTH_PROFILE)
    @PreAuthorize("hasAuthority('profile:edit')")
    public ResponseEntity<ProfileDto> updateUserById(String userId, UpdateUserRequest updateUserRequest) {
        var result = userService.updateByUserId(userId, updateUserRequest);
        return data(result);
    }
}
