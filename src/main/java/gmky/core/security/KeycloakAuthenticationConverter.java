package gmky.core.security;

import gmky.core.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class KeycloakAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final PermissionRepository permissionRepository;
    private final String principalClaimName;

    @Override
    @Transactional(readOnly = true)
    public AbstractAuthenticationToken convert(Jwt source) {
        var subject = source.getSubject();
        var permissions = permissionRepository.getPermissionsByUserId(subject).stream();
        Collection<GrantedAuthority> authorities = permissions.map(item -> {
            var resourceCode = item.getResourceCode();
            var permissionCode = item.getPermissionCode();
            var combinedResult = String.format("%s:%s", resourceCode, permissionCode);
            return new SimpleGrantedAuthority(combinedResult);
        }).collect(Collectors.toUnmodifiableList());
        return new JwtAuthenticationToken(source, authorities, source.getClaimAsString(principalClaimName));
    }
}
