package gmky.core.mapper;

import gmky.core.api.model.CreatePermissionSetRequest;
import gmky.core.api.model.UpdatePermissionSetRequest;
import gmky.core.dto.PermissionSetDto;
import gmky.core.entity.PermissionSet;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionSetMapper extends EntityMapper<PermissionSetDto, PermissionSet> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PermissionSet partialUpdate(UpdatePermissionSetRequest dto, @MappingTarget PermissionSet entity);

    PermissionSet toEntity(CreatePermissionSetRequest request);
}