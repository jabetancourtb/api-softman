package com.softman.configuration;

import com.softman.constant.SwaggerConstants;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(
        info = @Info(
                title = "API de prueba para Softman",
                version = "1.0",
                description = "Softman API"
        ),
        security = {
                @SecurityRequirement(name = SwaggerConstants.BEARER_AUTHENTICATION)
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfiguration {

}
