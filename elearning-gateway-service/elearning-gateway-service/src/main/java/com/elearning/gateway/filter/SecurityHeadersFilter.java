package com.elearning.gateway.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Adds HTTP security headers to every response.
 */
@Order(100)
@Component
public class SecurityHeadersFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return chain.filter(exchange)
                .doOnSubscribe(sub -> {
                    // Add headers BEFORE the response is committed
                    exchange.getResponse().beforeCommit(() -> {
                        HttpHeaders headers = exchange.getResponse().getHeaders();
                        headers.set("X-Content-Type-Options", "nosniff");
                        headers.set("X-Frame-Options", "DENY");
                        headers.set("X-XSS-Protection", "1; mode=block");
                        headers.set("X-Permitted-Cross-Domain-Policies", "none");
                        headers.set("Referrer-Policy", "no-referrer");
                        headers.set(
                                "Content-Security-Policy",
                                "default-src 'self'; frame-ancestors 'none'; object-src 'none';"
                        );
                        headers.set(
                                "Strict-Transport-Security",
                                "max-age=31536000; includeSubDomains; preload"
                        );
                        return Mono.empty();
                    });
                });
    }
}
