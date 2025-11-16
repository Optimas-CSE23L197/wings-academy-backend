package com.elearning_gateway_service.elearning_gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux // because gateway use webflux not mvc
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // Disable CSRF because Gateway is stateless & reactive
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // api gateway are stateless, so csrf is useless and breaks reactive chains

                // Disable HTTP Basic and Form Login for API gateway
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//              Gateways never show login forms; clients always send Bearer tokens.

                // Authorize routes
                .authorizeExchange(auth -> auth
                        .pathMatchers(
                                "/public/**",
                                "/actuator/**",
                                "/main/public/**"
                        ).permitAll()
                        // All other routes need authentication
                        .anyExchange().authenticated()
                )

//                enable oauth jwt validation
                .oauth2ResourceServer((oauth -> oauth
                        .jwt(Customizer.withDefaults()))
                )
                .build();
    }
}
