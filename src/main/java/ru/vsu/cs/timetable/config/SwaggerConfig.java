package ru.vsu.cs.timetable.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    private final BuildProperties buildProperties;
    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Backend of University timetable app")
                        .version(buildProperties.getVersion()));
    }
}
