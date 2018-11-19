package uk.gov.defra.datareturns.validation.submission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uk.gov.defra.datareturns.data.model.submission.Submission;
import uk.gov.defra.datareturns.data.model.submission.SubmissionStatus;
import uk.gov.defra.datareturns.validation.AbstractConstraintValidator;

import javax.validation.ConstraintValidatorContext;
import java.time.Year;

/**
 * Validate a {@link Submission} object
 *
 * @author Sam Gardner-Dell
 */
@RequiredArgsConstructor
@Slf4j
public class SubmissionValidator extends AbstractConstraintValidator<ValidSubmission, Submission> {
    @Override
    public void initialize(final ValidSubmission constraintAnnotation) {
        super.addChecks(this::checkContact, this::checkSubmissionStatus, this::checkSubmissionSeason, this::checkNotSubmittedWithNullMonth);
    }

    /**
     * Check that a contact id is provided
     *
     * @param submission the {@link Submission} to be validated
     * @param context    the validator context
     * @return true if valid, false otherwise
     */
    private boolean checkContact(final Submission submission, final ConstraintValidatorContext context) {
        return StringUtils.isNotBlank(submission.getContactId()) || handleError(context, "CONTACT_ID_REQUIRED", "contactId");
    }

    /**
     * Check that the submission status is provided
     *
     * @param submission the {@link Submission} to be validated
     * @param context    the validator context
     * @return true if valid, false otherwise
     */
    private boolean checkSubmissionStatus(final Submission submission, final ConstraintValidatorContext context) {
        return submission.getStatus() != null || handleError(context, "STATUS_REQUIRED", "status");
    }

    /**
     * Check that the season is provided and only allow submissions for the current or previous year
     *
     * @param submission the {@link Submission} to be validated
     * @param context    the validator context
     * @return true if valid, false otherwise
     */
    private boolean checkSubmissionSeason(final Submission submission, final ConstraintValidatorContext context) {
        final int currentSeason = Year.now().getValue();
        final int lastSeason = currentSeason - 1;
        return (submission.getSeason() != null && submission.getSeason() <= currentSeason && submission.getSeason() >= lastSeason)
                || handleError(context, "SEASON_INVALID", "season");
    }

    /**
     * Provides an example of an object-level constraint violation (where the error is caused because the values in 2 fields conflict)
     *
     * @param submission the {@link Submission} to be validated
     * @param context    the validator context
     * @return true if valid, false otherwise
     */
    private boolean checkNotSubmittedWithNullMonth(final Submission submission, final ConstraintValidatorContext context) {
        return (!SubmissionStatus.SUBMITTED.equals(submission.getStatus()) && submission.getMonth() != null)
                || handleError(context, "MONTH_REQUIRED_TO_SUBMIT", null);
    }


    @Override
    public String getErrorPrefix() {
        return "SUBMISSION";
    }
}
