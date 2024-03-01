package io.gaitian.reporter;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
abstract class CollaboraContainerTests {

    @Container
    static ReporterCompose compose =
            new ReporterCompose()
                    .withCollabora();

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("collabora.url", compose::getCollaboraUrl);
    }
}
