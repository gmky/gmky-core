package gmky.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static gmky.core.utils.AppUtil.logApplication;

@Configuration
@AutoConfiguration
@EnableJpaRepositories
@EntityScan(basePackages = {"gmky.core.entity"})
@ComponentScans({
        @ComponentScan(basePackages = {"gmky.core.web"}),
        @ComponentScan(basePackages = {"gmky.core.service"}),
        @ComponentScan(basePackages = {"gmky.core.mapper"}),
        @ComponentScan(basePackages = {"gmky.core.exception"})
})
@ConditionalOnProperty(name = "gmky.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class GmkyCoreAutoConfiguration {
    private final Environment environment;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStarted() {
        logApplication(environment);
    }
}

