package uk.gov.defra.datareturns.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Arrays;

/**
 * Spring security configuration
 *
 * @author Sam Gardner-Dell
 */
// FIXME: Prototype code - need to implement production ruleset
@SuppressWarnings({"NonFinalUtilityClass", "HideUtilityClassConstructor"})
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfiguration {
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    /**
     * Run the code associated with the given {@link Runnable} as a system user (effectively bypassing all security - use with caution!)
     *
     * @param invocable the {@link Runnable} to invoke without security - usually a lambda function.
     */
    public static void runAsSystemUser(final Runnable invocable) {
        final Authentication preInvocationAuthentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            final Authentication authentication = new PreAuthenticatedAuthenticationToken("system", null,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")));
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            invocable.run();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(preInvocationAuthentication);
        }
    }


    /**
     * Web security configuration
     *
     * @author Sam Gardner-Dell
     */
    @Configuration
    @ConditionalOnWebApplication
    static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().disable();
//                    .authorizeRequests()
//                    .antMatchers(AUTH_WHITELIST).permitAll()
//                    .antMatchers("/api/**").fullyAuthenticated().anyRequest()
//                    .authenticated()
//                    .and()
//                    .httpBasic();
        }
    }
}
