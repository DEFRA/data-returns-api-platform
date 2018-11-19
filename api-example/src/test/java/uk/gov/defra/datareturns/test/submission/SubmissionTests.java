package uk.gov.defra.datareturns.test.submission;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.data.model.submission.Submission;
import uk.gov.defra.datareturns.data.model.submission.SubmissionStatus;
import uk.gov.defra.datareturns.testcommons.framework.ApiContextTest;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Month;
import java.time.Year;
import java.util.Set;

import static uk.gov.defra.datareturns.testutils.IntegrationTestUtils.violationMessageMatching;

/**
 * Tests submission-level property validation
 */
@RunWith(SpringRunner.class)
@ApiContextTest
@Slf4j
public class SubmissionTests {
    @Inject
    private Validator validator;

    public static Submission createValidSubmission() {
        return createValidSubmission(RandomStringUtils.randomAlphanumeric(5), Year.now().getValue(), SubmissionStatus.INCOMPLETE);
    }

    public static Submission createValidSubmission(final String contactId, final Integer year, final SubmissionStatus status) {
        final Submission sub = new Submission();
        sub.setContactId(contactId);
        sub.setSeason(year.shortValue());
        sub.setStatus(status);
        sub.setMonth(Month.JANUARY);
        return sub;
    }

    /**
     * Return the year with the given offset from the current year computed from the system clock
     *
     * @param offset the offset to use (e.g. -1 for the previous year)
     * @return the year value calculated using the given offset
     */
    private static short getYear(final int offset) {
        return Integer.valueOf(Year.now().getValue() + offset).shortValue();
    }

    @Test
    public void testSubmissionWithoutContactIdFails() {
        final Submission sub = createValidSubmission();
        sub.setContactId(null);
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).haveExactly(1, violationMessageMatching("SUBMISSION_CONTACT_ID_REQUIRED"));
    }

    @Test
    public void testSubmissionWithoutStatusFails() {
        final Submission sub = createValidSubmission();
        sub.setStatus(null);
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).haveExactly(1, violationMessageMatching("SUBMISSION_STATUS_REQUIRED"));
    }

    @Test
    public void testSubmissionWithoutSeasonFails() {
        final Submission sub = createValidSubmission();
        sub.setSeason(null);
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).haveExactly(1, violationMessageMatching("SUBMISSION_SEASON_INVALID"));
    }

    @Test
    public void testSubmissionCurrentSeason() {
        final Submission sub = createValidSubmission();
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    public void testSubmissionPreviousSeason() {
        final Submission sub = createValidSubmission();
        sub.setSeason(getYear(-1));
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    public void testSubmissionYearTwoSeasonsPriorFails() {
        final Submission sub = createValidSubmission();
        sub.setSeason(getYear(-2));
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).haveExactly(1, violationMessageMatching("SUBMISSION_SEASON_INVALID"));
    }

    @Test
    public void testSubmissionYearInFutureFails() {
        final Submission sub = createValidSubmission();
        sub.setSeason(getYear(1));
        final Set<ConstraintViolation<Submission>> violations = validator.validate(sub);
        Assertions.assertThat(violations).haveExactly(1, violationMessageMatching("SUBMISSION_SEASON_INVALID"));
    }
}
