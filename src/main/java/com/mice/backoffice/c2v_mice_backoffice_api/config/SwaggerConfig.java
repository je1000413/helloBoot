package com.mice.backoffice.c2v_mice_backoffice_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.version}")
    private String appVersion;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("MiceBackOffice")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    public OpenApiCustomizer buildSecurityOpenApi(){
        Info info = new Info()
                .title("MICE Open API")
                .version(appVersion)
                .description("MICE Open API 정의서");

        String jwtSchemeName = "Authorization";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        return OpenApi -> OpenApi.info(info)
                .addSecurityItem(securityRequirement)
                .getComponents().addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }
}