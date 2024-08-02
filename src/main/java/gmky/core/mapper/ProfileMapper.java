package gmky.core.mapper;

import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.keycloak.KeycloakUserInfo;
import gmky.core.entity.Profile;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper extends EntityMapper<ProfileDto, Profile> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Profile partialUpdate(UpdateUserRequest dto, @MappingTarget Profile entity);

    @Mapping(target = "username", source = "preferredUsername")
    @Mapping(target = "userId", source = "sub")
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "status", expression = "java(gmky.core.enumeration.UserStatusEnum.ACTIVE)")
    @Mapping(target = "email", source = "email")
    Profile fromUserInfo(KeycloakUserInfo kcUserInfo);
}