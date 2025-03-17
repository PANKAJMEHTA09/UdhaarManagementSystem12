package com.pankaj.UdhaarManagementSystem.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@Slf4j
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        log.info("Initializing Swagger OpenAPI documentation...");
        return new OpenAPI().info(new Info()
                .title("Udhaar Management System API")
                .version("1.0")
                .description("API documentation for Udhaar Management System"));
    }
}
