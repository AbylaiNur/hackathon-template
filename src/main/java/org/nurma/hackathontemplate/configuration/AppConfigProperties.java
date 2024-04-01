package org.nurma.hackathontemplate.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
@Validated
public class AppConfigProperties {
    @NotBlank
    private String jwtAccessSecret;

    @NotBlank
    private String jwtRefreshSecret;
}
