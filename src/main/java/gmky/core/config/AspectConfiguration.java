package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import gmky.core.aop.FeatureFlagAspect;
import gmky.core.aop.LoggingAspect;
import gmky.core.repository.FeatureFlagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class AspectConfiguration {
    private final FeatureFlagRepository featureFlagRepository;

    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        log.info("Initializing Logging Aspect");
        return new LoggingAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public FeatureFlagAspect featureFlagAspect() {
        log.info("Initializing Feature Flag Aspect");
        return new FeatureFlagAspect(featureFlagRepository);
    }
}
