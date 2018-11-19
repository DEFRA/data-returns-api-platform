package uk.gov.defra.datareturns.test.platform;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.data.model.submission.Submission;
import uk.gov.defra.datareturns.data.model.submission.SubmissionRepository;
import uk.gov.defra.datareturns.data.model.submission.SubmissionStatus;
import uk.gov.defra.datareturns.testcommons.framework.ApiContextTest;

import javax.inject.Inject;
import java.time.Month;
import java.time.Year;

/**
 * Tests submission-level property validation
 */
@RunWith(SpringRunner.class)
@ApiContextTest
@Slf4j
public class BaseEntityTests {
    @Inject
    private SubmissionRepository submissionRepository;

    private static Submission createSampleSubmission() {
        final Submission sub = new Submission();
        sub.setContactId("BaseEntityTestsContact" + Math.random());
        sub.setSeason((short) Year.now().getValue());
        sub.setStatus(SubmissionStatus.INCOMPLETE);
        sub.setMonth(Month.FEBRUARY);
        return sub;
    }

    @Test
    public void testUnpersistedHashCodeEquals() {
        final Submission sub1 = createSampleSubmission();
        final Submission sub2 = createSampleSubmission();
        Assertions.assertThat(sub1).isNotEqualTo(sub2);
        Assertions.assertThat(sub1.hashCode()).isNotEqualTo(sub2.hashCode());
    }

    @Test
    public void testPersistedHashCodeEquals() {
        final Submission sub1 = submissionRepository.saveAndFlush(createSampleSubmission());
        final Submission sub2 = submissionRepository.saveAndFlush(createSampleSubmission());
        Assertions.assertThat(sub1).isNotEqualTo(sub2);
        Assertions.assertThat(sub1.hashCode()).isNotEqualTo(sub2.hashCode());
    }

    @Test
    public void testHashCodeEqualsAfterPersist() {
        final Submission sub = createSampleSubmission();
        final Long id = submissionRepository.saveAndFlush(sub).getId();
        final Submission persistedSub = submissionRepository.getOne(id);
        Assertions.assertThat(sub).isEqualTo(persistedSub);
        Assertions.assertThat(sub.hashCode()).isEqualTo(persistedSub.hashCode());
    }

    @Test
    public void tesNotEqualNull() {
        final Submission sub = createSampleSubmission();
        Assertions.assertThat(sub).isNotEqualTo(null);
    }

    @Test
    public void tesNotEqualAnotherType() {
        final Submission sub = createSampleSubmission();
        Assertions.assertThat(sub).isNotEqualTo(this);
    }
}
