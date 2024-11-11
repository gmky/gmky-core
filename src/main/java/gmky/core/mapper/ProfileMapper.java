package gmky.core.mapper;

import gmky.core.api.model.CreateUserRequest;
import gmky.core.api.model.FilterUserResponse;
import gmky.core.api.model.UpdateUserRequest;
import gmky.core.dto.ProfileDto;
import gmky.core.dto.keycloak.KeycloakUserInfo;
import gmky.core.entity.Profile;
import gmky.core.keycloak.api.model.KeycloakAdminCreateUserRequest;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

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

    @Mapping(target = "data", source = "content")
    @Mapping(target = "meta.total", source = "totalElements")
    @Mapping(target = "meta.page", source = "number")
    @Mapping(target = "meta.size", source = "size")
    FilterUserResponse toResponse(Page<Profile> page);

    KeycloakAdminCreateUserRequest toKeycloakRequest(CreateUserRequest request);

    Profile toEntity(CreateUserRequest request);
}