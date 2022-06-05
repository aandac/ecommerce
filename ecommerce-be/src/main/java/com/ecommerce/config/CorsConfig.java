package com.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders(
                        "Accept",
                        "Cache-Control",
                        "Origin",
                        "Content-Type",
                        "Content-Length",
                        "responseType",
                        "Authorization",
                        "X-Requested-With"
                )
                .allowedOrigins("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials","Access-Control-Allow-Methods")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH", "HEAD");
    }
}
