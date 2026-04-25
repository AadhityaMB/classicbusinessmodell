package com.classicbusinessmodel_schema.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Classic Business Model API")
                        .version("1.0.0")
                        .description("Professional API for managing customers, orders, products, and employees for the Classic Business Model.")
                        .contact(new Contact()
                                .name("Developer Team")
                                .email("support@classicbusiness.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
