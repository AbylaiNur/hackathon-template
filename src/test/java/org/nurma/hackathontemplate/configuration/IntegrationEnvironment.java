package org.nurma.hackathontemplate.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import org.nurma.hackathontemplate.util.GenerateKeys;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

abstract public class IntegrationEnvironment {
    private static final DockerImageName MONGO_IMAGE = DockerImageName.parse("mongo:7.0.5-jammy");
    public static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(MONGO_IMAGE);
    @Getter
    private static MongoTemplate mongoTemplate;

    static {
        MONGO_DB_CONTAINER.start();
        System.out.println("HERE IS THE MONGO URL: " + MONGO_DB_CONTAINER.getReplicaSetUrl());
        createMongoTemplate();
    }

    private static void createMongoTemplate() {
        MongoClient mongoClient = MongoClients.create(MONGO_DB_CONTAINER.getReplicaSetUrl());
        mongoTemplate = new MongoTemplate(mongoClient, "test");
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "test");

        registry.add("app.jwtAccessSecret", GenerateKeys::generateKey);
        registry.add("app.jwtRefreshSecret", GenerateKeys::generateKey);
    }
}
