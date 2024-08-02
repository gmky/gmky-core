package gmky.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gmky.core.enumeration.UserStatusEnum;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link gmky.core.entity.Profile}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDto implements Serializable {
    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
    Long id;
    String username;
    String userId;
    String fullName;
    UserStatusEnum status;
    Instant dob;
    String email;
    Instant lockedAt;
    Instant activatedAt;
}