package com.elearning_gateway_service.elearning_gateway_service.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(10)
@Component
public class JwtUserIdFilter implements WebFilter {

    private static final String USER_HEADER = "X-User-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .onErrorResume(e -> Mono.empty())       // if security context fails
                .switchIfEmpty(Mono.empty())            // guest user → do nothing
                .flatMap(auth -> {

                    if (!(auth instanceof Authentication a) ||
                            !(a.getPrincipal() instanceof Jwt jwt)) {
                        return chain.filter(exchange);
                    }

                    String userId = extractUserId(jwt);
                    if (userId == null || userId.isBlank()) {
                        return chain.filter(exchange);
                    }

                    // sanitize
                    userId = sanitize(userId);

                    // attach header
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header(USER_HEADER, userId)
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .switchIfEmpty(chain.filter(exchange)); // if everything was empty → continue
    }

    private String sanitize(String userId) {
        userId = userId.replaceAll("[^a-zA-Z0-9_-]", "");
        if (userId.length() > 64) {
            userId = userId.substring(0, 64);
        }
        return userId;
    }

    private String extractUserId(Jwt jwt) {
        if (jwt.hasClaim("userId")) return jwt.getClaimAsString("userId");
        if (jwt.hasClaim("uid")) return jwt.getClaimAsString("uid");
        if (jwt.hasClaim("id")) return jwt.getClaimAsString("id");
        return jwt.getSubject();
    }
}
