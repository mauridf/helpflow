package com.helpflow.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Libera endpoints públicos para desenvolvimento
                        .requestMatchers("/api/health/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/api/usuarios/**").permitAll() // TEMPORÁRIO - desenvolvimento
                        .requestMatchers("/api/tickets/**").permitAll()  // TEMPORÁRIO - desenvolvimento
                        // Todos os outros endpoints requerem autenticação
                        .anyRequest().authenticated()
                )
                // Desabilita CSRF para desenvolvimento
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}