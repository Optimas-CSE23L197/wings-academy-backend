package com.elearning.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // Disable CSRF because Gateway is stateless
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Stateless sessions — do NOT store authentication
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())

                // Disable logins (gateway uses JWT only)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                // Route-level authorization
                .authorizeExchange(auth -> auth
                        .pathMatchers(
                                "/public/**",
                                "/actuator/**",
                                "/main/public/**",
                                "/main/api/v1/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )

                // Enable JWT validation
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))

                .build();
    }
}
