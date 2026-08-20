package com.dev.sharing.credit.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
            return corsConfiguration;
        }));

        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(
                configurer -> configurer.jwt(
                        Customizer.withDefaults()
                )
        );
        return http.build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Profile("!local")
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(
                        "/v3/api-docs/**"
                ).cors(
                        AbstractHttpConfigurer::disable
                ).authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests.anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .authenticationManager(authentication -> {
                    var usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                    var username = usernamePasswordAuthenticationToken.getPrincipal();
                    var password = usernamePasswordAuthenticationToken.getCredentials().toString();
                    if (username.equals("admin") || password.equals("123456")) {
                        return new UsernamePasswordAuthenticationToken(username, password,
                                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
                    }

                    return authentication;
                });
        return http.build();
    }
}
