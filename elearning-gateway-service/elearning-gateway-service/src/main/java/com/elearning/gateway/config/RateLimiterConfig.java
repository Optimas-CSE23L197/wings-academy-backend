package com.elearning.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterConfig.class);

    public static final String USER_HEADER = "X-User-Id";

    private static final String IP_REGEX =
            "^((25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)\\.){3}"
          + "(25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)$|^[0-9a-fA-F:]+$";

    @Bean(name = "userKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {

            // Try to get user ID from header (added by JWT filter)
            String userId = exchange.getRequest().getHeaders().getFirst(USER_HEADER);

            if (userId != null && !userId.isBlank()) {

                // Sanitize user ID to avoid Redis key issues
                String sanitized = userId.replaceAll("[^a-zA-Z0-9_-]", "");

                if (sanitized.length() > 64) {
                    sanitized = sanitized.substring(0, 64);
                }

                return Mono.just("user:" + sanitized);
            }

            // Fallback: limit by client IP
            String ip = extractClientIp(exchange);
            return Mono.just("ip:" + ip);
        };
    }

    private String extractClientIp(ServerWebExchange exchange) {

        // 1. Try X-Forwarded-For header
        String xff = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");

        if (xff != null && !xff.isBlank()) {

            // Get first IP in list
            String ip = xff.split(",")[0].trim();

            // Validate candidate
            if (ip.matches(IP_REGEX)) {
                return ip;
            }
        }

        // 2. Fallback to remote address
        if (exchange.getRequest().getRemoteAddress() != null &&
            exchange.getRequest().getRemoteAddress().getAddress() != null) {

            String remoteIp = exchange.getRequest()
                    .getRemoteAddress()
                    .getAddress()
                    .getHostAddress();

            if (remoteIp != null && remoteIp.matches(IP_REGEX)) {
                return remoteIp;
            }
        }

        // 3. Safe fallback
        log.warn("Could not resolve client IP. Using fallback key: unknown");
        return "unknown";
    }
}
