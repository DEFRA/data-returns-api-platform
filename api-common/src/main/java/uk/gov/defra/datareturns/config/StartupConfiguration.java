package uk.gov.defra.datareturns.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Server startup configuration options
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "startup")
@RequiredArgsConstructor
@Slf4j
@Data
public class StartupConfiguration {
    private boolean printEnvironment;
}
