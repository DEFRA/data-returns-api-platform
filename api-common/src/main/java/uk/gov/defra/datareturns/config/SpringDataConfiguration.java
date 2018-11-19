package uk.gov.defra.datareturns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uk.gov.defra.datareturns.data.BaseRepository;

/**
 * Configuration for Spring JPA Repositories
 *
 * @author Sam Gardner-Dell
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = SpringDataConfiguration.DATA_PACKAGE,
        considerNestedRepositories = true,
        repositoryBaseClass = BaseRepository.BaseRepositoryImpl.class
)
@SuppressWarnings({"checkstyle:HideUtilityClassConstructor", "NonFinalUtilityClass"})
public class SpringDataConfiguration {
    public static final String DATA_PACKAGE = "uk.gov.defra";
}
