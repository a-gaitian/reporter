package io.gaitian.reporter.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "logging.level.io.gaitian=TRACE"
})
@SpringBootTest
public abstract class SpringBootTestBase {
}
