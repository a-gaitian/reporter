package io.gaitian.reporter;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.gaitian.reporter.template.processor.TemplateProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class TemplateProcessorTests {

    @Autowired
    private TemplateProcessor processor;

    @Test
    void simpleTemplate() throws IOException, XDocReportException {
        try (var is = getClass().getClassLoader().getResourceAsStream("Simple Template.docx")) {
            try (var os = new FileOutputStream("src/test/resources/Simple Template Result.docx")) {
                assert is != null;

                var metadata = new FieldsMetadata(TemplateEngineKind.Velocity);
                metadata.load("workers", Worker.class, true);
                metadata.addFieldAsImage("banner");

                var workers = new ArrayList<>();
                workers.add(new Worker("Иван", "Иванов", 30));
                workers.add(new Worker("Петр", "Петров", 31));
                workers.add(new Worker("Сергей", "Сергеев", 32));

                var random = new Random(System.currentTimeMillis());
                processor.process(
                        is,
                        Map.of(
                                "currentDate", OffsetDateTime.now().format(
                                        DateTimeFormatter.ofPattern("d MMMM yyyy г. HH:mm:ss", Locale.of("ru"))),
                                "randomNumber", random.nextInt(),
                                "randomDecimal", random.nextDouble(),
                                "strings", List.of("Строка 1", "Строка два", "Строка номер 3", "Четвёртая строка"),
                                "workers", workers,
                                "banner", new FileImageProvider(new File(getClass().getClassLoader().getResource("banner.png").getFile()))
                        ),
                        metadata,
                        os
                );
            }
        }
    }
}
