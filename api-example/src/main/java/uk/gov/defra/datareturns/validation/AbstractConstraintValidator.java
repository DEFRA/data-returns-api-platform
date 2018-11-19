package uk.gov.defra.datareturns.validation;

import uk.gov.defra.datareturns.validation.util.ValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Abstract implementation of the {@link ConstraintValidator} to simplify the validation logic of concrete subclasses
 *
 * @param <A> the annotation type handled by an implementation
 * @param <T> the target type supported by an implementation
 * @author Sam Gardner-Dell
 */
public abstract class AbstractConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {
    /**
     * The list of validation checks to be performed
     */
    private final List<CheckFunction<T>> checkFunctions = new ArrayList<>();

    @Override
    public final boolean isValid(final T value, final ConstraintValidatorContext context) {
        boolean valid = true;
        for (final CheckFunction<T> check : checkFunctions) {
            valid = check.apply(value, context) && valid;
        }
        return valid;
    }

    /**
     * Add checks to be performed when an object is validated
     *
     * @param checks the method(s) implementing the check functions to be executed when validating
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    protected final void addChecks(final CheckFunction<T>... checks) {
        this.checkFunctions.addAll(Arrays.asList(checks));
    }


    /**
     * The prefix used for all error messages generated by the concrete validator implementation
     *
     * @return the prefix to use
     */
    protected abstract String getErrorPrefix();


    /**
     * Validation error handler - adds the prefix to the given error code
     *
     * @param context the validator context
     * @param code    the error code uniquely identifying the validation error that occured
     * @param node    the node that caused the violations
     * @return always returns false (to signify a validation error)
     */
    protected final boolean handleError(final ConstraintValidatorContext context, final String code, final String node) {
        return ValidationUtil.handleError(context, getErrorPrefix() + "_" + code, node);

    }

    /**
     * Functional interface for a validation check
     *
     * @param <T> the generic type of the object to be checked
     */
    protected interface CheckFunction<T> extends BiFunction<T, ConstraintValidatorContext, Boolean> {
    }
}
