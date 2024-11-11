package ru.cloudfilestorage.cloudfilestorage.config;

import io.minio.MinioClient;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {

    @Bean
    public MinioClient getMinioClient(@Value("${spring.minio.url}") String url,
                                      @Value("${spring.minio.access-key}") String accessKey,
                                      @Value("${spring.minio.secret-key}") String secretKey,
                                      @Value("${spring.minio.port}") int port) {
        return MinioClient.builder()
                .endpoint(url, port, false)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("Cloud File Storage API")
                        .description("Application for storing and managing your files")
                        .version("1.0"));
    }

}
