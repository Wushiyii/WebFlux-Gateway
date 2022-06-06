package com.wushiyii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;


@EnableWebFlux
@SpringBootApplication
public class WebFluxGatewayApplication
{
    public static void main(String[] args) {
        SpringApplication.run(WebFluxGatewayApplication.class, args);
    }
}
