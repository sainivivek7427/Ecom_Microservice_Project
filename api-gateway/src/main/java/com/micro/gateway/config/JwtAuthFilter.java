package com.micro.gateway.config;

import io.jsonwebtoken.Claims;
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

        System.out.println("auth header "+authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//            return unAuthorizeResponse(exchange,"Missing Bearer token in authorization header");
            return MissingTokenResponse(exchange,"Missing Bearer token in authorization header");
//            throw new MissingTokenException("Missing Bearer token in authorization header",HttpStatus.FORBIDDEN);
        }

        String token = authHeader.substring(7);
        System.out.println(token);
        try {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//            String username=jwtUtil.extractUsername(token);
            Claims claims = jwtUtil.extractClaimsAllowExpired(token);

            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            System.out.println(username);
            System.out.println("Role "+role);
            boolean expired = jwtUtil.isTokenExpired(token);
            if ("guest".equals(role)) {
                System.out.println("expired "+expired);
                if (expired || !jwtUtil.validateGuestToken(token)) {
                    System.out.println("guest token expired ");
                    throw new UnauthorizedGuestException(
                            "Guest token expired or invalid",
                            HttpStatus.NOT_ACCEPTABLE
                    );
                }

            } else {

                if (expired || !jwtUtil.isTokenValid(token)) {
                    throw new UnAuthorizedUserException(
                            "User token expired or invalid",
                            HttpStatus.UNAUTHORIZED
                    );
                }
            }
            // You can pass username to downstream services
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User", username)
                    .header("X-Role", role != null ? role : "guest") // Set 'guest' role if undefined
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        }
        catch (UnauthorizedGuestException ex){
            return unAuthorizeGuestResponse(exchange,ex.getMessage());
        }

        catch (UnAuthorizedUserException ex){
            return unAuthorizeUserResponse(exchange,ex.getMessage());
        }
        catch (MissingTokenException ex){
            return MissingTokenResponse(exchange,ex.getMessage());
        }

        catch (Exception ex){
            return unAuthorizeResponse(exchange,ex.getMessage());
        }

    }


    private Mono<Void> unAuthorizeResponse(ServerWebExchange exchange,String message){
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        exchange.getResponse().getHeaders().add("Content-Type","application/json");
        String json=String.format("{\"status\":400,\"error\":\"UNAUTHORIZED\",\"message\":\"%s\",\"path\":\"%s\"}",message,
                exchange.getRequest().getPath());

        byte[] bytesResponse=json.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytesResponse)));
    }
    private Mono<Void> MissingTokenResponse(ServerWebExchange exchange,String message){
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().add("Content-Type","application/json");
        String json=String.format("{\"status\":403,\"error\":\"Forbidden\",\"message\":\"%s\",\"path\":\"%s\"}",message,
                exchange.getRequest().getPath());

        byte[] bytesResponse=json.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytesResponse)));
    }

    private Mono<Void> unAuthorizeGuestResponse(ServerWebExchange exchange,String message){
        exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
        exchange.getResponse().getHeaders().add("Content-Type","application/json");
        String json=String.format("{\"status\":406,\"error\":\"UNAUTHORIZED GUEST USER\",\"message\":\"%s\",\"path\":\"%s\"}",message,
                exchange.getRequest().getPath());

        byte[] bytesResponse=json.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytesResponse)));
    }
    private Mono<Void> unAuthorizeUserResponse(ServerWebExchange exchange,String message){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type","application/json");
        String json=String.format("{\"status\":401,\"error\":\"UNAUTHORIZED USER\",\"message\":\"%s\",\"path\":\"%s\"}",message,
                exchange.getRequest().getPath());

        byte[] bytesResponse=json.getBytes();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytesResponse)));
    }
    @Override
    public int getOrder() {
        return -1; // Highest priority
    }
}

