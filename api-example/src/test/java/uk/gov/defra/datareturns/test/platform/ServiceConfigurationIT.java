package uk.gov.defra.datareturns.test.platform;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.defra.datareturns.testcommons.framework.RestAssuredTest;
import uk.gov.defra.datareturns.testutils.IntegrationTestUtils;
import uk.gov.defra.datareturns.testutils.WithAuthenticatedUser;

/**
 * Integration tests to check server configuration
 */
@RunWith(SpringRunner.class)
@RestAssuredTest
@WithAuthenticatedUser
@Slf4j
public class ServiceConfigurationIT {
    @Test
    public void testCustomRepositoryConfiguration() {
        // Fires any mvc controllers which implement ResourceProcessor<RepositoryLinksResource> by loading the hal browser
        IntegrationTestUtils.getEntity("/").statusCode(200);
    }

    @Test
    public void testAlpsProfileAvailable() {
        IntegrationTestUtils.getEntity("/profile").statusCode(200);
    }


    @Test
    public void testSwaggerJsonAvailable() {
        // reset rest assured base path as swagger is served from outside of the /api context
        RestAssured.basePath = "/";
        IntegrationTestUtils.getEntity("/v2/api-docs").statusCode(200);
    }

    @Test
    public void testSwaggerUiAvailable() {
        // reset rest assured base path as swagger is served from outside of the /api context
        RestAssured.basePath = "/";
        IntegrationTestUtils.getEntity("/v2/api-docs").statusCode(200);
    }
}
