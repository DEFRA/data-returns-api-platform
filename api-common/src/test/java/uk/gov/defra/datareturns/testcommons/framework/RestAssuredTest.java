package uk.gov.defra.datareturns.testcommons.framework;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import uk.gov.defra.datareturns.testcommons.restassured.RestAssuredTestListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(
        basePackages = "uk.gov.defra",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "uk.gov.defra.*")
        }
)
@TestExecutionListeners(value = {RestAssuredTestListener.class},
                        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:h2-test.properties")
public @interface RestAssuredTest {
}
