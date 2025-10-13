package com.helpflow.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelpFlow API")
                        .description("Sistema de Gerenciamento de Tickets de Suporte")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipe HelpFlow")
                                .email("suporte@helpflow.com")));
    }
}