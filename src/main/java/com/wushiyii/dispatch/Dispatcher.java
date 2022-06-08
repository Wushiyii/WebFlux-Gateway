package com.wushiyii.dispatch;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

public class Dispatcher {

    private static final String URL_SEPARATOR = "/";
    private static final String QUERY_SEPARATOR = "?";

    public static String fetchUri(String module, String command, ServerHttpRequest request) {
        return aroundHost(fetchHostByModule(module)) + URL_SEPARATOR + module + URL_SEPARATOR + command + fetchQueryParams(request);
    }


    //可从配置中心、数据库、或者直接写死获取
    private static String fetchHostByModule(String module) {

        //示例
        if ("test".equals(module)) {
            return  "http://127.0.0.1:7400";
        }

        throw new RuntimeException("路径未配置映射，无法访问");
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


    private static String fetchQueryParams(ServerHttpRequest request) {
        String queryParams = "";
        if (!StringUtils.isEmpty(request.getURI().getQuery())) {
            queryParams = QUERY_SEPARATOR + request.getURI().getQuery();
        }
        return queryParams;
    }

}
