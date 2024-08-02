package gmky.core.web.rest.v1;

import gmky.core.api.AuthClientApi;
import gmky.core.api.model.LoginRequest;
import gmky.core.dto.keycloak.LoginWithUsernameAndPasswordResponse;
import gmky.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static gmky.core.utils.ResponseUtil.data;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthResource implements AuthClientApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<LoginWithUsernameAndPasswordResponse> login(LoginRequest loginRequest) {
        var result = authService.login(loginRequest);
        return data(result);
    }
}
