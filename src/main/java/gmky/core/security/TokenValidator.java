package gmky.core.security;

import gmky.core.enumeration.UserStatusEnum;
import gmky.core.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidator implements OAuth2TokenValidator<Jwt> {
    private final ProfileRepository profileRepository;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        List<OAuth2Error> errors = new ArrayList<>();
        var userId = token.getSubject();
        var profile = profileRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        if (UserStatusEnum.ACTIVE != profile.getStatus()) {
            errors.add(new OAuth2Error("DISABLED_USER", "Unauthorized", userId));
        }
        return OAuth2TokenValidatorResult.failure(errors);
    }
}
