package com.example.rakyatgamezomeapi.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationFilter authenticationFilter;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*"); // Ganti dengan asal yang diizinkan
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    source.registerCorsConfiguration("/**", config);
                    cors.configurationSource(source);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(config -> {
                    config.accessDeniedHandler(accessDeniedHandler);
                    config.authenticationEntryPoint(authenticationEntryPoint);
                })
                .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers("/swagger-ui/**", "/docs/api/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/tags/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/user/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/post/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/comment/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/product-coin/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/product-coin").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product-coin").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/product-coin/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tags").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tags").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tags").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/user/ban-user/").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/user/unban-user/").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/transaction/**").hasAuthority("USER")
                        .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
