package uk.gov.defra.datareturns.testcommons.framework;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@ComponentScan(
        basePackages = "uk.gov.defra",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "uk.gov.defra.*")
        }
)
@TestPropertySource(locations = "classpath:h2-test.properties")
public @interface ApiContextTest {
}
