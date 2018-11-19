package uk.gov.defra.datareturns.data.model.submission;

import org.springframework.data.rest.core.config.Projection;

/**
 * SimpleSubmissionProjection (hides everything except the contactId field)
 *
 * @author Sam Gardner-Dell
 */
@Projection(name = "simple", types = Submission.class)
@SuppressWarnings("unused")
public interface SimpleSubmissionProjection {
    String getContactId();
}
