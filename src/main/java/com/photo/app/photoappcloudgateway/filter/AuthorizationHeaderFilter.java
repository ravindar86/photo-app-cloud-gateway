package com.photo.app.photoappcloudgateway.filter;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter
        extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.AuthorizationConfig> {

    private Environment environment;

    public AuthorizationHeaderFilter(Environment environment) {
        super(AuthorizationConfig.class);
        this.environment = environment;
    }

    /**
     * Method to apply for the authorization config
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(AuthorizationConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            String authHeaderValue = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwtToken = authHeaderValue.replace("Bearer", "");

            if (!isJwtValid(jwtToken)) {
                return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    /**
     * Method to set the status code for error response
     * @param exchange
     * @param errorMsg
     * @param statusCode
     * @return
     */
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus statusCode) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(statusCode);

        return response.setComplete();
    }

    /**
     * Method to check whether the JWT token is valid
     * @param jwt
     * @return
     */
    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        System.out.println("Token:: "+environment.getProperty("token.secret.key"));
        System.out.println("Jwt:: "+jwt);

        String subject = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret.key"))
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        if (StringUtils.isEmpty(subject)) {
            returnValue = false;
        }

        return returnValue;
    }

    public static class AuthorizationConfig {

    }
}
