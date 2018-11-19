package uk.gov.defra.datareturns.test.platform;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.testcommons.framework.RestAssuredTest;
import uk.gov.defra.datareturns.testutils.IntegrationTestUtils;
import uk.gov.defra.datareturns.testutils.WithAuthenticatedUser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Integration tests to check server configuration
 */
@RunWith(SpringRunner.class)
@RestAssuredTest
@WithAuthenticatedUser
@Slf4j
public class ErrorHandlingIT {
    @Test
    public void testCustomRepositoryConfiguration() {
        // Fires any mvc controllers which implement ResourceProcessor<RepositoryLinksResource> by loading the hal browser
        IntegrationTestUtils.getEntity("/unknownEntity")
                .statusCode(404)
                .body("timestamp", notNullValue())
                .body("status", equalTo(404))
                .body("message", equalTo("Not Found"))
                .body("path", equalTo("/api/unknownEntity"));
    }
}
