package uk.gov.defra.datareturns.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.security.Security;

/**
 * Allows the Java DNS cache settings to be configured via spring-boot application configuration.
 *
 * @author Sam Gardner-Dell
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html">
 * https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html</a>
 */
@Configuration
@ConfigurationProperties(prefix = "dns")
@Getter
@Setter
@Validated
@Slf4j
public class DnsCacheConfiguration {
    public static final String DNS_CACHE_TTL_KEY = "networkaddress.cache.ttl";
    public static final String DNS_CACHE_NEGATIVE_TTL_KEY = "networkaddress.cache.negative.ttl";
    /**
     * Integer specifying the number of seconds successful name lookups will live in the cache.
     * if -1 cache forever, if 0 never cache, if > 0, cache for the given duration in seconds
     */
    @NonNull
    private Integer ttl = 60;

    /**
     * Integer specifying the number of seconds unsuccessful name lookups will live in the cache.
     * if -1 cache forever, if 0 never cache, if > 0, cache for the given duration in seconds
     */
    @NonNull
    private Integer negativeTtl = 10;

    @PostConstruct
    public void activate() {
        log.info("Setting JVM DNS cache ttl=" + ttl + " and negative-ttl=" + negativeTtl);
        Security.setProperty(DNS_CACHE_TTL_KEY, String.valueOf(getTtl()));
        Security.setProperty(DNS_CACHE_NEGATIVE_TTL_KEY, String.valueOf(getNegativeTtl()));
    }
}
