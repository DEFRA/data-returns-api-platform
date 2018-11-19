package uk.gov.defra.datareturns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Example API
 *
 * @author Sam Gardner-Dell
 */
@SpringBootApplication
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "NonFinalUtilityClass"})
public class ExampleApi {
    /**
     * Application main startup method
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ExampleApi.class, args);
    }
}
