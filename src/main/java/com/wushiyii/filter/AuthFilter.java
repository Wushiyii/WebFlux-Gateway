package com.wushiyii.filter;

import com.wushiyii.model.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 鉴权
 */
@Order(2)
@Component
public class AuthFilter implements WebFilter {

    private final static String TOKEN = "token";


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();

        //简单校验cookie
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie cookie = cookies.getFirst(TOKEN);
        if (Objects.isNull(cookie)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(ErrorResponse.fail("无权限访问").toJSON().getBytes())));
        }
        //调用权限系统查询token、获取用户可用权限等
        //...

        return webFilterChain.filter(serverWebExchange);
    }
}
