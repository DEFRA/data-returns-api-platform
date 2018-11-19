package uk.gov.defra.datareturns.test.submission;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.testcommons.framework.RestAssuredTest;
import uk.gov.defra.datareturns.testutils.WithAuthenticatedUser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static uk.gov.defra.datareturns.testutils.IntegrationTestUtils.createEntity;
import static uk.gov.defra.datareturns.testutils.IntegrationTestUtils.deleteEntity;
import static uk.gov.defra.datareturns.testutils.IntegrationTestUtils.getEntity;

/**
 * Integration tests for {@link uk.gov.defra.datareturns.data.model.submission.Submission}s
 */
@RunWith(SpringRunner.class)
@RestAssuredTest
@Slf4j
public class SubmissionIT {
    @Test
    @WithAuthenticatedUser
    public void testValidSubmission() {
        final String submissionJson = "{ \"contactId\": \"example1\", \"season\": 2018, \"status\": \"INCOMPLETE\", \"month\": \"JANUARY\" }";
        final String submissionUrl = createEntity("/submissions", submissionJson, (r) -> {
            r.statusCode(HttpStatus.CREATED.value());
            r.body("errors", nullValue());
        });

        getEntity(submissionUrl).statusCode(HttpStatus.OK.value())
                .body("contactId", equalTo("example1"))
                .body("season", equalTo(2018))
                .body("status", equalTo("INCOMPLETE"))
                .body("month", equalTo("JANUARY"));

        getEntity(submissionUrl + "?projection=simple").statusCode(HttpStatus.OK.value())
                .body("contactId", equalTo("example1"))
                .body("season", nullValue())
                .body("status", nullValue())
                .body("month", nullValue());
        deleteEntity(submissionUrl);
    }

    @Test
    @WithAuthenticatedUser
    public void testInvalidContactFails() {
        final String submissionJson = "{ \"contactId\": null, \"season\": 2018, \"status\": \"INCOMPLETE\", \"month\": \"FEBRUARY\" }";
        createEntity("/submissions", submissionJson, (r) -> {
            r.statusCode(HttpStatus.BAD_REQUEST.value());
            r.body("errors", hasSize(1));
            r.body("errors[0].entity", equalTo("Submission"));
            r.body("errors[0].property", equalTo("contactId"));
            r.body("errors[0].invalidValue", nullValue());
            r.body("errors[0].message", equalTo("SUBMISSION_CONTACT_ID_REQUIRED"));
        });
    }

    @Test
    @WithAuthenticatedUser
    public void testBlankContactFails() {
        final String submissionJson = "{ \"contactId\": \"  \", \"season\": 2018, \"status\": \"INCOMPLETE\", \"month\": \"MARCH\"  }";
        createEntity("/submissions", submissionJson, (r) -> {
            r.statusCode(HttpStatus.BAD_REQUEST.value());
            r.body("errors", hasSize(1));
            r.body("errors[0].entity", equalTo("Submission"));
            r.body("errors[0].property", equalTo("contactId"));
            r.body("errors[0].invalidValue", equalTo("  "));
            r.body("errors[0].message", equalTo("SUBMISSION_CONTACT_ID_REQUIRED"));
        });
    }

    @Test
    @WithAuthenticatedUser
    public void testSubmittedWithNullMonthFails() {
        final String submissionJson = "{ \"contactId\": \"submittedWithNullMonth\", \"season\": 2018, \"status\": \"SUBMITTED\", \"month\": null  }";
        createEntity("/submissions", submissionJson, (r) -> {
            r.statusCode(HttpStatus.BAD_REQUEST.value());
            r.body("errors", hasSize(1));
            r.body("errors[0].entity", equalTo("Submission"));
            r.body("errors[0].message", equalTo("SUBMISSION_MONTH_REQUIRED_TO_SUBMIT"));
        });
    }

    @Test
    public void testSubmissionRequiresAuth() {
        final String submissionJson = "{ \"contactId\": \"example2\", \"season\": 2018, \"status\": \"INCOMPLETE\" }";
        createEntity("/submissions", submissionJson, (r) -> r.statusCode(HttpStatus.UNAUTHORIZED.value()));
    }
}
