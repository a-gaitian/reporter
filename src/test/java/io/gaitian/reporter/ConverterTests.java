package io.gaitian.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;


class ConverterTests extends CollaboraContainerTests {

    @Autowired
    private Converter converter;

    @Test
    void loremIpsumDocxConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Lorem Ipsum.docx")) {
            try(var os = new FileOutputStream("src/test/resources/Lorem Ipsum.pdf")) {
                assert is != null;
                converter.docxToPdf(is, os);
            }
        }
    }

    @Test
    void demoDocxConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Demo.docx")) {
            try(var os = new FileOutputStream("src/test/resources/Demo.pdf")) {
                assert is != null;
                converter.docxToPdf(is, os);
            }
        }
    }
}
