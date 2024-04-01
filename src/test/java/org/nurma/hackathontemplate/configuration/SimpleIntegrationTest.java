package org.nurma.hackathontemplate.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SimpleIntegrationTest extends IntegrationEnvironment {

    @Test
    @DisplayName("Check that the context loads")
    void contextLoads() {
    }

    @Test
    @DisplayName("Check that the MongoDB container is up and running")
    void checkMongoDBContainerIsUp() {
        assertTrue(MONGO_DB_CONTAINER.isRunning());
    }
}