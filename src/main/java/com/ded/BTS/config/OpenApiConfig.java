package com.ded.BTS.config;

import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer dynamicServerCustomizer(
            HttpServletRequest request
    ) {
        return openApi -> {

            String serverUrl =
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .build()
                            .toUriString();

            openApi.setServers(
                    List.of(new Server()
                            .url(serverUrl)
                            .description("Current Environment"))
            );
        };
    }
}