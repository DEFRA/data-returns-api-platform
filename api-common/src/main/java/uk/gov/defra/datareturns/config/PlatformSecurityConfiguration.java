package uk.gov.defra.datareturns.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.gov.defra.datareturns.security.DefaultExpressionRoot;

/**
 * Spring security configuration
 *
 * @author Sam Gardner-Dell
 */
@Configuration
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "NonFinalUtilityClass"})
public class PlatformSecurityConfiguration {

    @RequiredArgsConstructor
    @EnableWebSecurity
    @ConditionalOnWebApplication
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        static final String[] WHITELIST = {
                // landing page
                "/",
                // swagger ui
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                // hal browser
                "/api/profile",
                "/api/",
                "/api/browser/**"
        };

        /**
         * Set up basic authentication on all routes except profile routes
         *
         * @param http the spring {@link HttpSecurity} builder
         * @throws Exception if a problem occurred configuring the authentication handlers
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.httpBasic().and().formLogin().and()
                    .csrf().disable()
                    .cors().disable()
                    .authorizeRequests()
                    .antMatchers(WHITELIST).permitAll()
                    .antMatchers("/api/**")
                    .authenticated();
        }
    }

    /**
     * Enable the use of security expressions in spring-data @{@link org.springframework.data.jpa.repository.Query}
     *
     * @author Sam Gardner-Dell
     */
    @Component
    public static class SecurityEvaluationContextExtension implements EvaluationContextExtension {
        @Override
        @NonNull
        public String getExtensionId() {
            return "dr-default";
        }

        @Override
        public DefaultExpressionRoot getRootObject() {
            return new DefaultExpressionRoot(SecurityContextHolder.getContext().getAuthentication());
        }
    }
}
