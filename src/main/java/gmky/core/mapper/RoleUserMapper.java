package gmky.core.mapper;

import gmky.core.dto.RoleUserDto;
import gmky.core.entity.RoleUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleUserMapper extends EntityMapper<RoleUserDto, RoleUser> {

}