package com.genill.swaggerConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/genill/api/*"))
                .apis(RequestHandlerSelectors.basePackage("com.genill"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Address Book API",
                "Sample API For Genill.com",
                "1.0",
                "Free To use",
                new springfox.documentation.service.Contact("Anish Niroula",
                        "https://genill.com", "anishniroula1@gmail.com"),
                "API License",
                "https://genill.com",
                Collections.emptyList());
    }

}
