package gmky.core.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@UtilityClass
public class AppUtil {
    public static void logApplication(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = StringUtils.defaultIfBlank(env.getProperty("server.port"), "8080");
        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("");
        String hostAddress = "localhost";
        String swaggerUrl = String.format("%s/swagger-ui/index.html", contextPath);
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                """
                                       \s
                        ----------------------------------------------------------------
                        Application '{}' is running! Access URLs:
                        Local\t\t: \t{}://localhost:{}{}
                        External\t: \t{}://{}:{}{}
                        SwaggerUI\t: \t{}://localhost:{}{}
                        Profiles\t: \t{}
                        ----------------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                protocol,
                serverPort,
                swaggerUrl,
                env.getActiveProfiles()
        );

    }
}
