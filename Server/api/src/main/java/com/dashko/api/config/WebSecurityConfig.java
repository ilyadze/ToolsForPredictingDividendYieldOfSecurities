package com.dashko.api.config;

import com.dashko.api.security.AuthenticationManager;
import com.dashko.api.security.BearerTokenServerAuthenticationConverter;
import com.dashko.api.security.JwtHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;


import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${jwt.secret}")
     String secret;

    final JwtRequestFilter jwtRequestFilter;

     final String [] publicRoutes = {"/api/auth/register", "/api/auth/login"};

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/secured").authenticated()
                                .requestMatchers("/info").authenticated()
                                .requestMatchers("/reply/**").authenticated()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .anyRequest().permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .exceptionHandling(exception-> {exception.authenticationEntryPoint( new HttpStatusEntryPoint( HttpStatus.UNAUTHORIZED));})
                .addFilterBefore(bearerAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private AuthenticationFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter bearerAuthenticationFilter = new AuthenticationFilter(authenticationManager,
                new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        return bearerAuthenticationFilter;
    }

}
