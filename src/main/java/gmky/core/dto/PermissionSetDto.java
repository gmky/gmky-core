package gmky.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gmky.core.enumeration.PermissionSetTypeEnum;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link gmky.core.entity.PermissionSet}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionSetDto implements Serializable {
    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
    Long id;
    String name;
    PermissionSetTypeEnum type;
    String description;
}