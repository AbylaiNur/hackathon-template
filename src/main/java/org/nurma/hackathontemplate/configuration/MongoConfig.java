package org.nurma.hackathontemplate.configuration;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.nurma.hackathontemplate.collection.User;
import org.nurma.hackathontemplate.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
@Service
@RequiredArgsConstructor
public class MongoConfig {
    private final AuthService authService;

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            try {
                User user = authService.getCurrentUserEntity();
                return Optional.of(user.getId());
            } catch (Exception ignored) {
                return Optional.empty();
            }
        };
    }
}
