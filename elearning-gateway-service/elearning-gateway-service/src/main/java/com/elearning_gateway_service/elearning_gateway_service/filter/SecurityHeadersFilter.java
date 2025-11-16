package com.elearning_gateway_service.elearning_gateway_service.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Adds HTTP security headers to every response.
 * Protects against clickjacking, XSS, MIME sniffing, downgrade attacks.
 */
@Order(100)  // Run after logging & JWT filters
@Component
public class SecurityHeadersFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return chain.filter(exchange).doOnSuccess(unused -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();

            // Prevent MIME sniffing
            headers.set("X-Content-Type-Options", "nosniff");

            // Prevent clickjacking
            headers.set("X-Frame-Options", "DENY");

            // XSS Protection (legacy, still good)
            headers.set("X-XSS-Protection", "1; mode=block");

            // Block cross-domain dangerous loading
            headers.set("X-Permitted-Cross-Domain-Policies", "none");

            // Referrer policy for privacy
            headers.set("Referrer-Policy", "no-referrer");

            // Recommended content security sample (example)
            headers.set("Content-Security-Policy",
                    "default-src 'self'; frame-ancestors 'none'; object-src 'none';");

            // HSTS — ONLY if using HTTPS in production
            headers.set("Strict-Transport-Security",
                    "max-age=31536000; includeSubDomains; preload");
        });
    }
}
