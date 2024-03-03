package io.gaitian.reporter;

import io.gaitian.reporter.base.SpringBootTestBase;
import io.gaitian.reporter.template.converter.ConverterBean;
import io.gaitian.reporter.template.converter.ConverterProvider;
import io.gaitian.reporter.template.converter.annotation.Converts;
import io.gaitian.reporter.template.model.Format;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ConverterProviderTests.FakeConverterConfig.class)
@SpringBootTest(classes = ConverterProvider.class)
class ConverterProviderTests extends SpringBootTestBase {

    @Converts(from = Format.DOCX, to = Format.PDF)
    static class FakeConverter implements ConverterBean {
        @Override
        public void convert(Format from, InputStream is, Format to, OutputStream os) {/*do nothing*/}
    }

    @TestConfiguration
    static class FakeConverterConfig {
        @Bean
        public ConverterBean mockConverter() {
            return mock(FakeConverter.class);
        }
    }

    @Autowired
    ConverterBean mockConverter;

    @Autowired
    ConverterProvider provider;

    @Test
    void docxToPdfConverter() {
        var converter = provider.getConverter(Format.DOCX, Format.PDF);

        var is = mock(InputStream.class);
        var os = mock(OutputStream.class);

        converter.convert(is, os);

        verify(mockConverter, times(1)).convert(Format.DOCX, is, Format.PDF, os);
    }

    @Test
    void pdfToDocxNotFound() {
        assertThrows(IllegalArgumentException.class, () -> provider.getConverter(Format.PDF, Format.DOCX));
    }
}
