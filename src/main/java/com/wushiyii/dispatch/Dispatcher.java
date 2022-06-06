package com.wushiyii.dispatch;

import org.springframework.http.server.reactive.ServerHttpRequest;

public class Dispatcher {


    public static String fetchUri(ServerHttpRequest request) {

        return "http://127.0.0.1:7400" + request.getPath();
    }


}
