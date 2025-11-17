package com.elearning.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Global CORS configuration for Spring Cloud Gateway.
 * Allows safe communication with frontend apps (React, Angular, etc.)
 */
@Configuration
public class CorsConfig {

    @Bean
    @Order(0) // Run BEFORE all security filters
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // ⚠️ IMPORTANT: Set your frontend domain here
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",     // React dev
                "http://localhost:4200",     // Angular dev
                "https://your-frontend.com", // Production front-end
                "http://127.0.0.1:5501"
        ));

        // Allow methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Allow headers
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // Allow authorization (JWT)
        config.setAllowCredentials(true);

        // Cache preflight responses
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
