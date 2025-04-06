package com.ridex.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ridex.repository.UserRepository;
import com.ridex.utils.JwtFilter;


@Configuration
public class SecurityConfig {
    
    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;
    

    // ✅ Constructor Injection for JwtFilter
    public SecurityConfig(JwtFilter jwtFilter,UserRepository userRepository) {
        this.jwtFilter = jwtFilter;
        this.userRepository = userRepository;
    }

    // ✅ Password Encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure password hashing
    }

    // ✅ UserDetailsService: Retrieve user details (roles, authorities, etc.)
    @Bean
    public UserDetailsService UserDetailsService() {
    	return new com.ridex.config.UserServiceRole(userRepository);
    }
 

    // ✅ Authentication Manager for Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // ✅ Security Configuration for HTTP Requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for JWT-based APIs (stateless)
            .authorizeHttpRequests(auth -> auth
                // ✅ Public Endpoints (no authentication required)
                .requestMatchers("/api/user/verify", "/api/user/register", "/otp/send", "/otp/verify",
                                 "/api/riders/login", "/api/riders/register", "/api/riders/profile").permitAll()

                // ✅ Role-based access control
                .requestMatchers("/api/user/**").hasAuthority("ROLE_RIDER")  // Riders can access rider-specific endpoints
                .requestMatchers("/api/drivers/**").hasAuthority("ROLE_DRIVER")  // Drivers can access driver-specific endpoints
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")  // Admins can access admin-specific endpoints
                .anyRequest().authenticated() // Other endpoints require authentication

            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session (JWT-based)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before Spring's default UsernamePasswordAuthenticationFilter

        return http.build();
    }
}

