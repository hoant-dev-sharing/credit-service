package com.dev.sharing.creditservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme =  "bearer",
        bearerFormat = "JWT"
)
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:8001/oauth2/authorize",
                        tokenUrl = "http://localhost:8001/oauth2/token",
                        scopes = {
                                @OAuthScope(
                                        name = "profile"
                                )
                        }
                )
        )
)
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "oauth2")
)
public class OpenApiConfig {
}
