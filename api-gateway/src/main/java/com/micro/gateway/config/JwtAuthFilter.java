package com.micro.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        System.out.println("path ");
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("path "+path);
        // Allow public auth APIs
        if (path.startsWith("/auth"))
            return chain.filter(exchange);

        // Get Bearer token
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
            return unAuthorizeResponse(exchange,"Missing Bearer token in authorization header");
        }

        String token = authHeader.substring(7);

        try {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean istokenValid=jwtUtil.isTokenValid(token);
            System.out.println("is token valid "+istokenValid);
            String username=null;
            if(istokenValid){
                 username= jwtUtil.extractUsername(token);

            }

            // You can pass username to downstream services
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User", username)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (Exception e) {
            return unAuthorizeResponse(exchange,"Invalid Token or expired");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
        }
    }


    private Mono<Void> unAuthorizeResponse(ServerWebExchange exchange,String message){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type","application/json");
        String json=String.format("{\"status\":401,\"error\":\"UNAUTHORIZED\",\"message\":\"%s\",\"path\":\"%s\"}",message,
                exchange.getRequest().getPath());

        byte[] bytesResponse=json.getBytes();
//        return exchange.getResponse()
//                .writeWith(Mono.just(exchange.getResponse()
//                        .bufferFactory()
//                        .wrap(bytesResponse)));
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytesResponse)));
    }
    @Override
    public int getOrder() {
        return -1; // Highest priority
    }
}

