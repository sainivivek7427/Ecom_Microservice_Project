package com.micro.auth.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class JwtFilter{}
//        extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserDetailServiceimpl userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // Skip JWT filter for auth endpoints
//        String path = request.getServletPath();
//        if (path.startsWith("/auth")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String header = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (header != null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//
//            try {
//                username = jwtUtil.extractUsername(token);
//            } catch (Exception e) {
//                System.out.println("Invalid token: " + e.getMessage());
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.isTokenValid(token, userDetails)) {
//
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
////@Component
////public class JwtFilter {}
////        extends OncePerRequestFilter {
////    @Autowired
////    private JwtUtil jwtUtil;
////    @Autowired private UserDetailServiceimpl userDetailsService;
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request,
////                                    HttpServletResponse response,
////                                    FilterChain filterChain) throws ServletException, IOException {
////        System.out.println("Auth token filter ..................");
////        final String header = request.getHeader("Authorization");
////        String token = null;
////        String username = null;
////
////        if (header != null && header.startsWith("Bearer ")) {
////            System.out.println("Bearer if condition");
////            token = header.substring(7);
////            try {
////                username = jwtUtil.extractUsername(token);
////            } catch (Exception ignored) {}
////        }
////
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////// Suggested code may be subject to a license. Learn more: ~LicenseLog:3490337289.
////            System.out.println("username not null");
////            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////            if(isBlacklisted(token)){
////                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////                response.getWriter().write("Token is blacklisted");
////
////                return;
////            }
////            if (jwtUtil.isTokenValid(token, userDetails)) {
////                UsernamePasswordAuthenticationToken authToken =
////                        new UsernamePasswordAuthenticationToken(
////                                userDetails, null, userDetails.getAuthorities()
////                        );
////                SecurityContextHolder.getContext().setAuthentication(authToken);
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }
////
////    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
////
////    public void blacklistToken(String token) {
////        blacklistedTokens.add(token);
////    }
////
////    public boolean isBlacklisted(String token) {
////        return blacklistedTokens.contains(token);
////    }
////}
