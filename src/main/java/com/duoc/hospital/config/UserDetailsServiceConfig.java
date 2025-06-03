package com.duoc.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Clase de configuración para usuarios en memoria
@Configuration
public class UserDetailsServiceConfig {

    // Método que crea el servicio de usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        // Usuario administrador para pruebas
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin") // Contraseña sin cifrar, solo para pruebas
                .roles("ADMIN")
                .build();

        // Usuario normal para pruebas
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user") // Contraseña sin cifrar, solo para pruebas
                .roles("USER")
                .build();

        // Devuelve el gestor de usuarios en memoria
        return new InMemoryUserDetailsManager(admin, user);
    }
}