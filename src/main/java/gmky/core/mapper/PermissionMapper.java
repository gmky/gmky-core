package gmky.core.mapper;

import gmky.core.api.model.FilterPermissionResponse;
import gmky.core.dto.PermissionDto;
import gmky.core.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper extends EntityMapper<PermissionDto, Permission> {
    @Mapping(target = "data", source = "content")
    @Mapping(target = "meta.total", source = "totalElements")
    @Mapping(target = "meta.page", source = "number")
    @Mapping(target = "meta.size", source = "size")
    FilterPermissionResponse toResponse(Page<Permission> page);
}