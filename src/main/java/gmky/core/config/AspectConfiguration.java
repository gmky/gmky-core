package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import gmky.core.aop.LoggingAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class AspectConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect() {
        log.info("Initializing Logging Aspect");
        return new LoggingAspect();
    }
}
