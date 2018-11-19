package uk.gov.defra.datareturns.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

/**
 * Configuration for springfox swagger
 *
 * @author Sam Gardner-Dell
 */
@Configuration
@ConditionalOnWebApplication
@EnableSwagger2WebMvc
@Import(springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class)
public class SwaggerConfiguration {
    @Bean
    public Docket api(final ApplicationContext context) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .enableUrlTemplating(false)
                .apiInfo(apiInfo(context));
    }


    private ApiInfo apiInfo(final ApplicationContext context) {
        return new ApiInfo(context.getId(), "Api Documentation",
                "1.0", "urn:tos", DEFAULT_CONTACT,
                "OGLv3", "http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/",
                new ArrayList<>());
    }
}
