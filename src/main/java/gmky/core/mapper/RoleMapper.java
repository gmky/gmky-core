package gmky.core.mapper;

import gmky.core.api.model.CreateRoleRequest;
import gmky.core.api.model.FilterRoleResponse;
import gmky.core.api.model.UpdateRoleRequest;
import gmky.core.dto.RoleDto;
import gmky.core.entity.Role;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(UpdateRoleRequest dto, @MappingTarget Role entity);

    Role toEntity(CreateRoleRequest request);

    @Mapping(target = "data", source = "content")
    @Mapping(target = "meta.total", source = "totalElements")
    @Mapping(target = "meta.page", source = "number")
    @Mapping(target = "meta.size", source = "size")
    FilterRoleResponse toResponse(Page<Role> page);
}