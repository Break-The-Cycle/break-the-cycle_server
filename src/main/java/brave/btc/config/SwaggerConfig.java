package brave.btc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Break The Cycle",
                description = "Break The Cycle REST API DOCS",
                version = "v1"),
        servers = {@Server(url="/api", description = "spring boot context path")})
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi registerOpenApi() {
        String[] paths = {"/v1/**"};
        return GroupedOpenApi.builder()
                .group("BRAVE")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}