package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import gmky.core.interceptor.JwtTokenInterceptor;
import gmky.core.keycloak.ApiClient;
import gmky.core.keycloak.api.KeycloakAdminClientApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class KeycloakAdminApiConfiguration {
    @Value("${gmky.core.keycloak.base-url}")
    private String baseUrl;

    @Value("${gmky.core.keycloak.realm}")
    private String realm;

    @Bean
    public RestTemplate keycloakAdminRestTemplate() {
        var restTemplate = new RestTemplate();
        var interceptors = restTemplate.getInterceptors();
        interceptors.add(new JwtTokenInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(KeycloakAdminClientApi.class)
    public KeycloakAdminClientApi keycloakAdminClientApi() {
        var baseUrlWithRealm = String.format("%s/admin/realms/%s", baseUrl, realm);
        var keycloakAdminClientApi = new KeycloakAdminClientApi();
        var clientApi = new ApiClient(keycloakAdminRestTemplate());
        clientApi.setBasePath(baseUrlWithRealm);
        keycloakAdminClientApi.setApiClient(clientApi);
        return keycloakAdminClientApi;
    }
}
