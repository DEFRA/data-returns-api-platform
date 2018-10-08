package uk.gov.defra.datareturns.testcommons.restassured;

import com.google.common.base.Charsets;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.filter.log.LogDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.rules.ExternalResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static io.restassured.RestAssured.preemptive;

@ConditionalOnWebApplication
@Service
@Slf4j
public class RestAssuredRule extends ExternalResource {
    @Inject
    private EmbeddedWebApplicationContext context;

    @Override
    protected void before() {
        setupRestAssured();
    }

    @Override
    protected void after() {
    }

    private void setupRestAssured() {
        final int port = context.getEmbeddedServletContainer().getPort();
        log.info("Setting up Rest Assured on port {}", port);
        RestAssured.reset();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api";
        RestAssured.authentication = this.getAuthentication();
        RestAssured.config().getEncoderConfig().defaultContentCharset(Charsets.UTF_8);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @SuppressWarnings("WeakerAccess")
    protected AuthenticationScheme getAuthentication() {
        return preemptive().basic("admin", "password");
    }
}
