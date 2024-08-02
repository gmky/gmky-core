package gmky.core.mapper;

import gmky.core.dto.PermissionDto;
import gmky.core.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper extends EntityMapper<PermissionDto, Permission> {

}