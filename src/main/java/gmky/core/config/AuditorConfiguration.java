package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import gmky.core.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Slf4j
@Configuration
@EnableJpaAuditing
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class AuditorConfiguration {
    @Bean
    @ConditionalOnMissingBean(AuditorAware.class)
    public AuditorAware<String> auditorProvider() {
        log.info("Initializing Auditor Provider");
        return () -> Optional.ofNullable(SecurityUtil.getCurrentUsername());
    }
}
