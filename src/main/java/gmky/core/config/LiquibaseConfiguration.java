package gmky.core.config;

import gmky.core.GmkyCoreAutoConfiguration;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(GmkyCoreAutoConfiguration.class)
public class LiquibaseConfiguration {
    private final DataSource dataSource;

    @Value("${spring.liquibase.change-log:classpath:/db/changelog/db.changelog-master.yaml}")
    private String changeLog;

    @Value("${gmky.liquibase.change-log:/gmky/db/changelog/000.changelog-master.xml}")
    private String gmkyChangeLog;

    @Bean
    @ConditionalOnProperty(name = "gmky.liquibase.enabled", havingValue = "true", matchIfMissing = true)
    public SpringLiquibase gmkyCoreLiquibase() {
        log.info("Initializing GMKY Core Liquibase ...");
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(gmkyChangeLog);
        return liquibase;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.liquibase.enabled", havingValue = "true", matchIfMissing = true)
    public SpringLiquibase springLiquibase() {
        log.info("Initializing Liquibase ...");
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        return liquibase;
    }
}
