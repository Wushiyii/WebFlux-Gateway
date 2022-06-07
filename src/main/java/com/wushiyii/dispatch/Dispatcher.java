package com.wushiyii.dispatch;


import org.springframework.http.server.reactive.ServerHttpRequest;

public class Dispatcher {

    private static final String URL_SEPARATOR = "/";
    private static final String QUERY_SEPARATOR = "?";

    public static String fetchUri(String module, String command, ServerHttpRequest request) {

        return fetchHostByModule(module) + URL_SEPARATOR + module + URL_SEPARATOR + command + QUERY_SEPARATOR + request.getURI().getQuery();
    }


    //可从配置中心、数据库、或者直接写死获取
    private static String fetchHostByModule(String module) {

        //示例
        String host = "";
        if ("test".equals(module)) {
            host = "http://127.0.0.1:7400";
        }

        return aroundHost(host);

    }

    private static String aroundHost(String host) {
        if (host.startsWith(URL_SEPARATOR)) {
            host = host.substring(1);
        }
        if (host.endsWith(URL_SEPARATOR)) {
            host = host.substring(0, host.length() - 1);
        }
        return host;
    }


}
