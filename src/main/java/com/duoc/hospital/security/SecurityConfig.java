package com.duoc.hospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final SecurityApiKeyFilter securityApiKeyFilter;

    public SecurityConfig(SecurityApiKeyFilter securityApiKeyFilter) {
        this.securityApiKeyFilter = securityApiKeyFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/pacientes/**", "/api/v1/medicos/**").hasRole("ADMIN") // Solo ADMIN
                        .requestMatchers("/api/v1/atenciones/**").hasAnyRole("ADMIN", "USER") // ADMIN o USER
                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )
                .httpBasic(Customizer.withDefaults()) // Habilitar autenticación básica
                .addFilterBefore(securityApiKeyFilter, UsernamePasswordAuthenticationFilter.class) // Agregar filtro de API Key
                .build();
    }
}