package com.elearning.gateway.filter;

import org.springframework.core.annotation.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.elearning.gateway.filter.GlobalLoggingFilter;

import reactor.core.publisher.Mono;

/**
 * Global logging filter: logs method, path, userId, client IP, status and latency.
 * Non-blocking and designed for Spring Cloud Gateway / WebFlux.
 */
@Order(50)
@Component
public class GlobalLoggingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);
    private static final String USER_HEADER = "X-User-Id";
    private static final String START_TIME_ATTR = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Capture start time
        exchange.getAttributes().put(START_TIME_ATTR, System.currentTimeMillis());

        // Capture early IP (safe to compute once)
        String ip = extractClientIp(exchange.getRequest());

        return chain.filter(exchange)
                .doOnSuccess(unused -> logRequest(exchange, ip))
                .doOnError(err -> logRequestWithError(exchange, ip, err));
    }

    private void logRequest(ServerWebExchange exchange, String ip) {
        long start = (long) exchange.getAttributes().getOrDefault(START_TIME_ATTR, System.currentTimeMillis());
        long latency = System.currentTimeMillis() - start;

        String method = (exchange.getRequest().getMethod() != null)
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";

        String path = exchange.getRequest().getURI().getPath();
        String userId = exchange.getRequest().getHeaders().getFirst(USER_HEADER);

        int status = exchange.getResponse().getStatusCode() != null ?
                exchange.getResponse().getStatusCode().value() : 0;

        log.info("req method={} path={} userId={} ip={} status={} latencyMs={}",
                method, path, nullSafe(userId), ip, status, latency);
    }

    private void logRequestWithError(ServerWebExchange exchange, String ip, Throwable err) {
        long start = (long) exchange.getAttributes().getOrDefault(START_TIME_ATTR, System.currentTimeMillis());
        long latency = System.currentTimeMillis() - start;

        String method = (exchange.getRequest().getMethod() != null)
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";

        String path = exchange.getRequest().getURI().getPath();
        String userId = exchange.getRequest().getHeaders().getFirst(USER_HEADER);

        String message = err == null ? "unknown" :
                err.getClass().getSimpleName() + ": " + err.getMessage();

        log.error("req method={} path={} userId={} ip={} status=ERROR latencyMs={} error={}",
                method, path, nullSafe(userId), ip, latency, message);
    }

    private String extractClientIp(ServerHttpRequest request) {
        String xff = request.getHeaders().getFirst("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        if (request.getRemoteAddress() != null &&
                request.getRemoteAddress().getAddress() != null) {
            return request.getRemoteAddress().getAddress().getHostAddress();
        }
        return "unknown";
    }

    private String nullSafe(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }
}
