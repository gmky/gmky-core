package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import gmky.core.keycloak.ApiClient;
import gmky.core.keycloak.api.KeycloakClientApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class KeycloakApiConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String keycloakUri;

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(KeycloakClientApi.class)
    public KeycloakClientApi keycloakClientApi() {
        var keycloakClientApi = new KeycloakClientApi();
        var apiClient = new ApiClient();
        apiClient.setBasePath(keycloakUri);
        keycloakClientApi.setApiClient(apiClient);
        return keycloakClientApi;
    }
}
