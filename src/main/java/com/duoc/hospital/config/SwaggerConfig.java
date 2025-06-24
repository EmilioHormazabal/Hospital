package com.duoc.hospital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Hospital Duoc UC")
                        .version("1.0")
                        .description("Documentación de la API para gestión de atenciones médicas en el Hospital V&M.")
                        .contact(new Contact()
                                .name("Emilio Hormazabal")
                                .email("emi.hormazabal@duocuc.cl")
                                .url("https://github.com/EmilioHormazabal/Hospital")
                        )
                );
    }
}