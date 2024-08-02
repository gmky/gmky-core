package gmky.core.service;

import gmky.core.api.model.LoginRequest;
import gmky.core.dto.keycloak.LoginWithUsernameAndPasswordResponse;

public interface AuthService {
    LoginWithUsernameAndPasswordResponse login(LoginRequest request);
}
