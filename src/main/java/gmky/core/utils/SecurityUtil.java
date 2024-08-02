package gmky.core.utils;

import gmky.core.service.impl.ApplicationContextProvider;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

@UtilityClass
public class SecurityUtil {
    public static final String USER_ID_CLAIM_KEY = "sub";
    public static final String PRINCIPAL_CLAIM_KEY_PROPERTY = "spring.security.oauth2.resourceserver.jwt.principal-claim-name";

    public static String getCurrentUsername() {
        var ctxHolder = SecurityContextHolder.getContext();
        var authentication = ctxHolder.getAuthentication();
        if (authentication == null) return null;
        var principal = authentication.getPrincipal();
        if (principal instanceof String username) {
            return username;
        } else if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (principal instanceof Jwt jwt) {
            var claimName = ApplicationContextProvider.getProperty(PRINCIPAL_CLAIM_KEY_PROPERTY);
            return jwt.getClaimAsString(claimName);
        }
        return null;
    }

    public static String getCurrentUserId() {
        var ctxHolder = SecurityContextHolder.getContext();
        var authentication = ctxHolder.getAuthentication();
        if (authentication == null) return null;
        var principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt.getClaimAsString(USER_ID_CLAIM_KEY);
        }
        return null;
    }
}
