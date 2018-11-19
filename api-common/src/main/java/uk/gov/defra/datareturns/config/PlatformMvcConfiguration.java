package uk.gov.defra.datareturns.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Error handling configuration
 *
 * @author Sam Gardner-Dell
 */
@Configuration
public class PlatformMvcConfiguration {

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnMissingBean(value = ErrorController.class)
    public BasicErrorController basicErrorController(final ErrorAttributes errorAttributes, final ServerProperties serverProperties) {
        return new BasicErrorController(errorAttributes, serverProperties.getError(), Collections.emptyList());
    }

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return (registry) -> registry.addErrorPages(new ErrorPage("/error"));
    }
}
