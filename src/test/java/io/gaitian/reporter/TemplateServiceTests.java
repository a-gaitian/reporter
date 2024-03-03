package io.gaitian.reporter;

import io.gaitian.reporter.base.CollaboraContainerTests;
import io.gaitian.reporter.template.TemplateService;
import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;

class TemplateServiceTests extends CollaboraContainerTests {

    @Autowired
    private TemplateService templateService;

    @Test
    void simpleTemplate() throws IOException {
        try (var is = getClass().getClassLoader().getResourceAsStream("Simple Template.docx")) {
            try (var os = new FileOutputStream("src/test/resources/Simple Template Result.pdf")) {
                assert is != null;
                var result = templateService
                        .compose(new ReadOnlyFile(is, Format.DOCX), SimpleTemplateData.DATA, Format.PDF);

                result.getInputStream().transferTo(os);
            }
        }
    }
}
