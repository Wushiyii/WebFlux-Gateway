package com.wushiyii.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebFluxExecutors {

    @Bean
    public ExecutorService requestWorkerPool() {
        return new ThreadPoolExecutor(4,
                4,
                120,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(8192),
                new ThreadFactoryBuilder().setNameFormat("request-worker-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

}
