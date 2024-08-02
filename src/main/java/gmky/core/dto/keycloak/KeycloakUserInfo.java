package gmky.core.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class KeycloakUserInfo {
    @JsonAlias("sub")
    private String sub;
    @JsonAlias("email")
    private String email;
    @JsonAlias("given_name")
    private String givenName;
    @JsonAlias("family_name")
    private String familyName;
    @JsonAlias("preferred_username")
    private String preferredUsername;
    @JsonAlias("email_verified")
    private String emailVerified;
    @JsonAlias("name")
    private String name;
}
