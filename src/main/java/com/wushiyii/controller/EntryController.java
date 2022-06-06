package com.wushiyii.controller;

import com.wushiyii.dispatch.Dispatcher;
import com.wushiyii.model.Constants;
import com.wushiyii.model.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@RestController
public class EntryController {

    @Resource
    private ExecutorService requestWorkerPool;

    //异步http请求发送者
    private final WebClient webClient = WebClient.create();

    @PostMapping("/{module}/{command}")
    public Mono<Void> process(@PathVariable("module") String module,
                              @PathVariable("command") String command,
                              DefaultServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders requestHeaders = request.getHeaders();


        MediaType contentType = Objects.nonNull(requestHeaders.getContentType()) ?
                requestHeaders.getContentType() : MediaType.TEXT_PLAIN;


        return webClient.method(HttpMethod.POST)
                .uri(Dispatcher.fetchUri(request))
                .contentType(contentType)
                .headers(httpHeaders -> {
                    httpHeaders.addAll(requestHeaders);
                    httpHeaders.remove(Constants.HOST);
                })
                .body(BodyInserters.fromDataBuffers(request.getBody()))
                .exchange()
                .timeout(Duration.ofMillis(10000))
                .publishOn(Schedulers.fromExecutorService(requestWorkerPool))
                .onErrorResume(e -> Mono.defer(() -> response.writeWith(Mono.just(response.bufferFactory()
                                .wrap(ErrorResponse.fail(e).toJSON().getBytes()))))
                        .then(Mono.empty()))
                .flatMap(clientResponse -> {
                    response.setStatusCode(clientResponse.statusCode());
                    response.getHeaders().putAll(clientResponse.headers().asHttpHeaders());
                    return response.writeWith(clientResponse.bodyToMono(DataBuffer.class));
                });
    }

}
