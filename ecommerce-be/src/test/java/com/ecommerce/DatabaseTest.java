package com.ecommerce;

import com.ecommerce.config.AwsS3Config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class DatabaseTest {

    @Container
    public static final PostgreSQLContainer postgres
            = (PostgreSQLContainer) new PostgreSQLContainer("postgres:alpine3.16")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432)
            .withReuse(true);

    @BeforeAll
    public static void setup() {
        postgres.start();
    }

    @DynamicPropertySource
    public static void datasourceConfig(DynamicPropertyRegistry registry) {
        // postgres
        var postgresUrl = "jdbc:postgresql://" + postgres.getHost() + ":" + postgres.getMappedPort(5432) + "/postgres";
        registry.add(
                "spring.datasource.url",
                () -> postgresUrl
        );
        registry.add(
                "spring.liquibase.url",
                () -> postgresUrl
        );
        registry.add(
                "spring.liquibase.user",
                () -> "test"
        );
        registry.add(
                "spring.liquibase.password",
                () -> "test"
        );
    }
}
