package gmky.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link gmky.core.entity.Permission}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDto implements Serializable {
    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
    Long id;
    String permissionCode;
    String resourceCode;
    String description;
}