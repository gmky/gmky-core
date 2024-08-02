package gmky.core.mapper;

import gmky.core.api.model.CreateRoleRequest;
import gmky.core.api.model.UpdateRoleRequest;
import gmky.core.dto.RoleDto;
import gmky.core.entity.Role;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(UpdateRoleRequest dto, @MappingTarget Role entity);

    Role toEntity(CreateRoleRequest request);
}