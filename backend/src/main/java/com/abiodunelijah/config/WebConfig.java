package com.abiodunelijah.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration for CORS (Cross-Origin Resource Sharing)
 * Enables the frontend running on localhost:5173 to communicate with the backend on localhost:8080
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure CORS mappings for the application
     * This allows the React frontend to make requests to the Spring Boot backend
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/api/v1/**")
                .allowedOrigins(
                        "http://localhost:5174",
                        "http://localhost:5173"      // React development server

                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders(
                        "Content-Type",
                        "Authorization",
                        "X-Requested-With",
                        "Accept",
                        "Origin"
                )
                .allowCredentials(true)
                .maxAge(3600);
    }
}

