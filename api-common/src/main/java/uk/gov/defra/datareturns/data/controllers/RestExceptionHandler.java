package uk.gov.defra.datareturns.data.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.core.ValidationErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link RestExceptionHandler} allows the project to override the default exception handling from
 * spring data rest which is defined in {@link org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler}
 *
 * @author Sam Gardner-Dell
 */
@ControllerAdvice(basePackageClasses = org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Component
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSourceAccessor messageSourceAccessor;

    /**
     * Create a new {@link RestExceptionHandler}
     *
     * @param applicationContext the spring application context
     */
    @Inject
    public RestExceptionHandler(final ApplicationContext applicationContext) {
        this.messageSourceAccessor = new MessageSourceAccessor(applicationContext);
    }


    /**
     * Handle {@link HttpMessageNotReadableException}s - extends the default functionality to intercept exceptions generated where the root
     * cause is an {@link AccessDeniedException} and issue a suitable response code for these cases.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NonNull
    ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull final HttpMessageNotReadableException ex,
                                                        @NonNull final HttpHeaders headers,
                                                        @NonNull final HttpStatus status,
                                                        @NonNull final WebRequest request) {
        Throwable root = ex.getMostSpecificCause();
        if (root instanceof AccessDeniedException) {
            AccessDeniedError error = AccessDeniedError.of("Access to associated resource denied", ex.getMessage());
            return super.handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }


    /**
     * Handles {@link RepositoryConstraintViolationException}s by returning {@code 400 Bad Request}.
     * <p>
     * This handler has been added to workaround an issue in spring data rest whereby class-level validation error
     * are not returned if the object being persisted is the top-level object.
     * <p>
     * It was observed that the pollution inventory Submission class-level validation error were not output whereas
     * the class-level validation error for Releases/Transfers were output as these are nested within the Submission object.
     * It doesn't appear that this issue affects field-level validation error.
     * <p>
     * At the time of writing, these issues are outstanding but assigned to the SDR team:
     * <a href="https://jira.spring.io/browse/DATAREST-926">DATAREST-926</a>
     * <a href="https://jira.spring.io/browse/DATAREST-832">DATAREST-832</a>
     *
     * @param exception the exception to handle.
     * @return a {@link ResponseEntity} containing the data to be serialized on the REST response.
     */
    @ExceptionHandler
    @SuppressWarnings("unused")
    ResponseEntity<ConstraintViolationExceptionMessage> handleRepositoryConstraintViolationException(
            final RepositoryConstraintViolationException exception) {
        return new ResponseEntity<>(new ConstraintViolationExceptionMessage(exception, this.messageSourceAccessor),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * Custom validation error response - as per the spring data rest implementation but also extracts global error.
     *
     * @author Sam Gardner-Dell
     */
    @Getter
    private static final class ConstraintViolationExceptionMessage {
        @JsonProperty("errors")
        private final List<ValidationError> errors = new ArrayList<>();

        /**
         * Create a new {@link ConstraintViolationExceptionMessage} for the given exception
         *
         * @param exception the {@link RepositoryConstraintViolationException} to convert into a response
         * @param accessor  the {@link MessageSourceAccessor} providing access to the i18n {@link org.springframework.context.MessageSource}
         */
        private ConstraintViolationExceptionMessage(final RepositoryConstraintViolationException exception, final MessageSourceAccessor accessor) {
            Assert.notNull(exception, "RepositoryConstraintViolationException must not be null!");
            Assert.notNull(accessor, "MessageSourceAccessor must not be null!");
            final ValidationErrors validationErrors = (ValidationErrors) exception.getErrors();

            for (final ObjectError objectError : validationErrors.getGlobalErrors()) {
                this.errors.add(ValidationError.of(objectError.getObjectName(), null, null, accessor.getMessage(objectError)));
            }

            for (final FieldError fieldError : validationErrors.getFieldErrors()) {
                this.errors.add(ValidationError.of(fieldError.getObjectName(), fieldError.getField(),
                        fieldError.getRejectedValue(), accessor.getMessage(fieldError)));
            }
        }
    }

    /**
     * Response data for constraint violation exceptions
     */
    @AllArgsConstructor(staticName = "of")
    @Getter
    private static class ValidationError {
        private String entity;
        private String property;
        private Object invalidValue;
        private String message;
    }

    /**
     * Response data for access denied error
     */
    @AllArgsConstructor(staticName = "of")
    @Getter
    private static class AccessDeniedError {
        private String error;
        private String cause;
    }
}
