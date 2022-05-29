package com.ecommerce.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ecommerceAPI() {
        String securitySchemeName = "bearer-key";
        var securitySchemesItem = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .in(In.HEADER)
                .bearerFormat("JWT");

        return new OpenAPI().components(new Components().addSecuritySchemes(
                                securitySchemeName,
                                securitySchemesItem
                        )
                )
                .addServersItem(new Server().url("/"))
                .info(new Info().title("Ecommerce API")
                        .description("Ecommerce application backend API documentation")
                        .version("v1.0.0"));

    }
}
