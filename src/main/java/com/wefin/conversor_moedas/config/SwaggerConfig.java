package com.wefin.conversor_moedas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The Class SwaggerConfig
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Conversor de Moedas")
                        .version("1.0.0"))
                .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Conversor da Cidade de Wefin")));
    }
}
