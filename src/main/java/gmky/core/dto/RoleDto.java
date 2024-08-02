package gmky.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gmky.core.enumeration.RoleTypeEnum;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link gmky.core.entity.Role}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto implements Serializable {
    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
    Long id;
    String name;
    String description;
    RoleTypeEnum type;
    Boolean isEnable;
    Boolean isDefault;
}