package gmky.core.mapper;

import gmky.core.api.model.CreatePermissionSetRequest;
import gmky.core.api.model.UpdatePermissionSetRequest;
import gmky.core.dto.PermissionSetDto;
import gmky.core.entity.PermissionSet;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionSetMapper extends EntityMapper<PermissionSetDto, PermissionSet> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PermissionSet partialUpdate(UpdatePermissionSetRequest dto, @MappingTarget PermissionSet entity);

    PermissionSet toEntity(CreatePermissionSetRequest request);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "meta.total", source = "totalElements")
    @Mapping(target = "meta.page", source = "number")
    @Mapping(target = "meta.size", source = "size")
    gmky.core.api.model.FilterPermissionSetResponse toResponse(Page<PermissionSet> permissionSets);
}