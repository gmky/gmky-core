package gmky.core.web.rest.v1;

import gmky.core.aop.FeatureFlag;
import gmky.core.api.AuthClientApi;
import gmky.core.api.model.LoginRequest;
import gmky.core.dto.keycloak.LoginWithUsernameAndPasswordResponse;
import gmky.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static gmky.core.common.Constants.FF_AUTH_LOGIN;
import static gmky.core.utils.ResponseUtil.data;

@Slf4j
@RestController
@RequiredArgsConstructor
@FeatureFlag(FF_AUTH_LOGIN)
public class AuthResource implements AuthClientApi {
    private final AuthService authService;

    @Override
    @FeatureFlag(FF_AUTH_LOGIN)
    public ResponseEntity<LoginWithUsernameAndPasswordResponse> login(LoginRequest loginRequest) {
        var result = authService.login(loginRequest);
        return data(result);
    }
}
