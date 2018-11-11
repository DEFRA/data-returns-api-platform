package uk.gov.defra.datareturns.testcommons.restassured;

import com.google.common.base.Charsets;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Test listener to ensure that the rest assured framework is properly configured prior to running any tests.
 * <p>
 * This listener will observe any spring security {@link org.springframework.security.test.context.support.WithMockUser} annotations and properly
 * configure RestAssured to use basic authentication with the username/password given in the annotation.
 *
 * @author Sam Gardner-Dell
 */
@Slf4j
public class RestAssuredTestListener extends AbstractTestExecutionListener implements TestExecutionListener {
    @Override
    public void beforeTestClass(final TestContext testContext) {
        if (testContext.getApplicationContext() instanceof ServletWebServerApplicationContext) {
            final ServletWebServerApplicationContext context = (ServletWebServerApplicationContext) testContext.getApplicationContext();
            RestAssured.reset();
            RestAssured.baseURI = "http://localhost";
            RestAssured.port = context.getWebServer().getPort();
            RestAssured.basePath = "/api";
            RestAssured.config().getEncoderConfig().defaultContentCharset(Charsets.UTF_8);
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        }
    }


    @Override
    public void beforeTestMethod(final TestContext testContext) {
        RestAssured.authentication = RestAssured.DEFAULT_AUTH;
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            final User usr = (User) auth.getPrincipal();
            RestAssured.authentication = RestAssured.preemptive().basic(usr.getUsername(), usr.getPassword());
        }
    }
}
