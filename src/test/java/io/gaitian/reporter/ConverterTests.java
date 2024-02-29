package io.gaitian.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class ConverterTests {

    @Autowired
    private Converter converter;

    @Test
    void loremIpsumConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Lorem Ipsum.docx")) {
            try(var os = new FileOutputStream("Lorem Ipsum.pdf")) {
                assert is != null;
                converter.docxToPdf(is, os);
            }
        }
    }

    @Test
    void demoConvert() throws IOException {
        try(var is = getClass().getClassLoader().getResourceAsStream("Demo.docx")) {
            try(var os = new FileOutputStream("Demo.pdf")) {
                assert is != null;
                converter.docxToPdf(is, os);
            }
        }
    }
}
