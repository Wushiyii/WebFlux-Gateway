package com.wushiyii.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.wushiyii.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 限流
 */
@Component
public class LimitFilter implements WebFilter {

    RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

        ServerHttpResponse response = serverWebExchange.getResponse();
        boolean acquired = rateLimiter.tryAcquire();
        if (!acquired) {
            response.setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(ErrorResponse.fail("超过流量限制，无法访问").toJSON().getBytes())));
        }

        return webFilterChain.filter(serverWebExchange);



    }
}
