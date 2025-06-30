package com.duoc.hospital.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
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
                        .title("API Hospital V&M")
                        .version("1.0.0")
                        .description("""
                    <h3>API para gestión hospitalaria</h3>
                    <p>Documentación completa de los endpoints para:</p>
                    <ul>
                        <li>👨‍⚕️ Gestión de médicos</li>
                        <li>👥 Gestión de pacientes</li>
                        <li>📆 Gestión de atenciones médicas</li>
                        <li>📊 Reportes y consultas especializadas</li>
                    </ul>
                    """)
                        .contact(new Contact()
                                .name("Emilio Hormazabal - Ingeniería en Informática")
                                .email("emi.hormazabal@duocuc.cl")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa")
                        .url("https://github.com/EmilioHormazabal/Hospital"));
    }
}
