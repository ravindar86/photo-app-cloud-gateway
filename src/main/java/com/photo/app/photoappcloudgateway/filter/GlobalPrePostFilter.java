package com.photo.app.photoappcloudgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalPrePostFilter{

    private Logger logger = LoggerFactory.getLogger(GlobalPostFilter.class);

    @Order(1)
    @Bean
    public GlobalFilter secondCustomGlobalFilter(){
        return (exchange, chain) ->{
            logger.info("2nd Pre Filter Invoked..");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("2nd Post Filter Invoked..");
            }));
        };
    }

    @Order(2)
    @Bean
    public GlobalFilter thirdCustomGlobalFilter(){
        return (exchange, chain) ->{
            logger.info("3rd Pre Filter Invoked..");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("3rd Post Filter Invoked..");
            }));
        };
    }
}
