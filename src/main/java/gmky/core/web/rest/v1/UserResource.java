package gmky.core.web.rest.v1;

import gmky.core.api.UserClientApi;
import gmky.core.api.model.SummaryItemDto;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.enumeration.UserStatusEnum;
import gmky.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gmky.core.utils.ResponseUtil.data;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserResource implements UserClientApi {
    private final UserService userService;

    @Override
    @PreAuthorize("hasAnyAuthority('profile:view')")
    public ResponseEntity<List<ProfileDto>> filterUsers(String username, String email, String fullName, List<UserStatusEnum> status, Pageable pageable) {
        var page = userService.filterUsers(username, email, fullName, status, pageable);
        return data(page);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('profile:view', 'profile:edit', 'profile:delete', 'profile:approve')")
    public ResponseEntity<ProfileDto> getUserDetailById(String userId) {
        var result = userService.getProfileByUserId(userId);
        return data(result);
    }

    @Override
    public ResponseEntity<ProfileDto> profile() {
        var profile = userService.getCurrentUserProfile();
        return data(profile);
    }

    @Override
    public ResponseEntity<List<SummaryItemDto>> summary() {
        var summary = userService.getCurrentUserSummary();
        return data(summary);
    }

    @Override
    @PreAuthorize("hasAuthority('profile:edit')")
    public ResponseEntity<ProfileDto> updateUserById(String userId, UpdateUserRequest updateUserRequest) {
        var result = userService.updateByUserId(userId, updateUserRequest);
        return data(result);
    }
}
