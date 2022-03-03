package com.photo.app.photoappcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;


@Component
public class GlobalPreFilter implements GlobalFilter, Ordered {
    private Logger logger = LoggerFactory.getLogger(GlobalPreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Inside Global Pre Filter...");

        String requestPath = exchange.getRequest().getPath().toString();
        logger.info("Request Path: "+ requestPath);

        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        Set<String> headerNames = httpHeaders.keySet();

        headerNames.stream().forEach(headerName -> {
            System.out.println(headerName+" -> "+httpHeaders.get(headerName));
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
