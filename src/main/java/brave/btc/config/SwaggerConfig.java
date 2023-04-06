package brave.btc.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
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

}