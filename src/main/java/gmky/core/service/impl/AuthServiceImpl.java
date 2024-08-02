package gmky.core.service.impl;

import gmky.core.api.model.LoginRequest;
import gmky.core.dto.keycloak.LoginWithUsernameAndPasswordResponse;
import gmky.core.keycloak.api.KeycloakClientApi;
import gmky.core.mapper.ProfileMapper;
import gmky.core.repository.ProfileRepository;
import gmky.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static gmky.core.common.Constants.KEYCLOAK_DEFAULT_GRANT_TYPE;
import static gmky.core.common.Constants.KEYCLOAK_DEFAULT_SCOPE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String clientSecret;

    private final KeycloakClientApi keycloakClientApi;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public LoginWithUsernameAndPasswordResponse login(LoginRequest request) {
        var tokenInfo = keycloakClientApi.loginWithUsernameAndPassword(KEYCLOAK_DEFAULT_GRANT_TYPE, clientId, clientSecret, request.getUsername(), request.getPassword(), KEYCLOAK_DEFAULT_SCOPE);
        var userInfo = keycloakClientApi.getCurrentUserInfo(buildAuthorizationHeader(tokenInfo.getAccessToken()));
        var profile = profileRepository.findByUserId(userInfo.getSub()).orElse(null);
        if (profile == null) {
            profile = profileMapper.fromUserInfo(userInfo);
            profileRepository.save(profile);
        }
        return tokenInfo;
    }

    private String buildAuthorizationHeader(String accessToken) {
        return String.format("Bearer %s", accessToken);
    }
}
