package com.elearning.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@Component
@Order(50)
public class GlobalLoggingFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);
    private static final String USER_HEADER = "X-User-Id";
    private static final String START_TIME_ATTR = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        exchange.getAttributes().put(START_TIME_ATTR, System.currentTimeMillis());
        String ip = extractClientIp(exchange.getRequest());

        return chain.filter(exchange)
                .doFinally(signal -> logRequest(exchange, ip, signal));
    }

    private void logRequest(ServerWebExchange exchange, String ip, SignalType signal) {
        long start = (long) exchange.getAttributes().getOrDefault(START_TIME_ATTR, System.currentTimeMillis());
        long latency = System.currentTimeMillis() - start;

        String method = exchange.getRequest().getMethod() != null
                ? exchange.getRequest().getMethod().name()
                : "UNKNOWN";

        String path = exchange.getRequest().getURI().getPath();
        String userId = exchange.getRequest().getHeaders().getFirst(USER_HEADER);

        int status = exchange.getResponse().getStatusCode() != null
                ? exchange.getResponse().getStatusCode().value()
                : 0;

        if (signal == SignalType.ON_ERROR) {
            log.error("req method={} path={} userId={} ip={} status={} latencyMs={} message=Exception thrown",
                    method, path, nullSafe(userId), ip, status, latency);
        } else {
            log.info("req method={} path={} userId={} ip={} status={} latencyMs={}",
                    method, path, nullSafe(userId), ip, status, latency);
        }
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

    @Bean
        public WebFilter debugCorsFilter() {
            return (exchange, chain) -> {
                return chain.filter(exchange)
                        .doOnSuccess(done -> {
                            org.springframework.http.HttpHeaders h = exchange.getResponse().getHeaders();
                            if (h.containsKey("Access-Control-Allow-Origin")) {
                                System.out.println("🔥 CORS ORIGIN FOUND → " + h.get("Access-Control-Allow-Origin"));
                            }
                        });
            };
    }

}
