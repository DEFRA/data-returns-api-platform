package uk.gov.defra.datareturns.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * Security expression root for RCR, allows additional expressions to be added
 *
 * @author Sam Gardner-Dell
 */
@Getter
@Setter
public class DefaultExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;
    private Object target;

    public DefaultExpressionRoot(final Authentication authentication) {
        super(authentication);
    }

    @Override
    public Object getThis() {
        return target;
    }
}
