package uk.gov.defra.datareturns.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * Configuration settings for the airbrake log appender
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "airbrake")
@Getter
@Setter
public class AirbrakeConfiguration {
    private boolean enabled;
    private String env;
    private String projectKey;
    private URI host;
    private String apiPath;
}
