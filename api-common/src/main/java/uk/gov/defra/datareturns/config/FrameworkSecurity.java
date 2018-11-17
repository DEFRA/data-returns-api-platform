package uk.gov.defra.datareturns.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.gov.defra.datareturns.security.DefaultExpressionRoot;

/**
 * Spring security configuration
 *
 * @author Sam Gardner-Dell
 */
@SuppressWarnings({"NonFinalUtilityClass", "HideUtilityClassConstructor"})
@Configuration
@Slf4j
public class FrameworkSecurity {

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
