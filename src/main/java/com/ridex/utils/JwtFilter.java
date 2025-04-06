package com.ridex.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class); // Using SLF4J for logging

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                String role = jwtUtil.extractRole(token);
                String phone = jwtUtil.extractPhoneNumber(token);
                System.out.println(" phone number :"+phone);
                

                

                if (phone.isEmpty() || role.isEmpty()) {
                    log.error("JWT Parsing Error: Phone or Role is null/empty");
                    throw new IllegalArgumentException("Invalid JWT Token");
                }

                System.out.println("Extracted user ID: " + role+"  :"+ phone );
                System.out.println("lets see "+SecurityContextHolder.getContext().getAuthentication());
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails;
                    
                    try {
                        userDetails = userDetailsService.loadUserByUsername(phone);
                    } catch (RuntimeException e) {
                        log.error("User not found for phone: {}", phone);
                        throw e;
                    }

                    if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        log.debug("✅ Authentication set for user: {}", phone);
                    } else {
                        log.error("❌ JWT Token validation failed for phone: {}", phone);
                    }
                }
            } catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage());
            }

        }

        filterChain.doFilter(request, response); // Proceed with the request
    }
}
