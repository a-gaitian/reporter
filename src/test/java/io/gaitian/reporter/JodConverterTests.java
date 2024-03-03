package io.gaitian.reporter;

import io.gaitian.reporter.base.CollaboraContainerTests;
import io.gaitian.reporter.template.converter.JodConverter;
import io.gaitian.reporter.template.model.file.Format;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;


class JodConverterTests extends CollaboraContainerTests {

    @Autowired
    private JodConverter jodConverter;

    @Test
    void loremIpsumDocxConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Lorem Ipsum.docx")) {
            try(var os = new FileOutputStream("src/test/resources/Lorem Ipsum.pdf")) {
                assert is != null;
                jodConverter.convert(Format.DOCX, is, Format.PDF, os);
            }
        }
    }

    @Test
    void demoDocxConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Demo.docx")) {
            try(var os = new FileOutputStream("src/test/resources/Demo.pdf")) {
                assert is != null;
                jodConverter.convert(Format.DOCX, is, Format.PDF, os);
            }
        }
    }
}
