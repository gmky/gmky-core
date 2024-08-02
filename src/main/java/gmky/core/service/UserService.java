package gmky.core.service;

import gmky.core.api.model.SummaryItemDto;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.enumeration.UserStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    ProfileDto getCurrentUserProfile();

    List<SummaryItemDto> getCurrentUserSummary();

    ProfileDto getProfileByUserId(String userId);

    ProfileDto updateByUserId(String userId, UpdateUserRequest request);

    Page<ProfileDto> filterUsers(String username, String email, String fullName, List<UserStatusEnum> statuses, Pageable pageable);
}
