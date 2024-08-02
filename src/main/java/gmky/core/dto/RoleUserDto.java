package gmky.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link gmky.core.entity.RoleUser}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleUserDto implements Serializable {
    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
    Long id;
    String userId;
}