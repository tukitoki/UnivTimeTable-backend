package ru.vsu.cs.timetable.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme().type(HTTP).scheme("bearer").bearerFormat("JWT")
                        )
                )
                .info(new Info().title("Backend of University timetable app")
                        .version(buildProperties.getVersion()));
    }
}
