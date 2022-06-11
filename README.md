# WebFlux-Gateway

基于WebFlux、WebClient实现的简单API网关

### 特性
- 基于WebFLux实现
- Http使用WebClient调用，异步IO
- 兼容所有Http方法、Content-Type
- 支持鉴权、限流等过滤器
- 框架非常轻量，代码不超过300行

TODO
- 支持RPC调用
- 支持SPI实现过滤器
- 支持统计模块

### Example
```shell
curl 'http://127.0.0.1:8080/entry/{module}/{command}'
```

- module: 系统模块
- command: 具体功能
