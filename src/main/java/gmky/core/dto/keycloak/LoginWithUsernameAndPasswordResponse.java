package gmky.core.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginWithUsernameAndPasswordResponse {
    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("refresh_token")
    private String refreshToken;

    @JsonAlias("token_type")
    private String tokenType;

    @JsonAlias("scope")
    private String scope;

    @JsonAlias("id_token")
    private String idToken;

    @JsonAlias("expires_in")
    private Long expiresIn;

    @JsonAlias("refresh_expires_in")
    private Long refreshExpiresIn;

    @JsonAlias("not-before-policy")
    private boolean notBeforePolicy;

    @JsonAlias("session_state")
    private String sessionState;
}
