package io.gaitian.reporter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class Converter {
    private final DocumentConverter converter;

    @SneakyThrows
    public void docxToPdf(InputStream is, OutputStream os) {
        converter.convert(is)
                .as(DefaultDocumentFormatRegistry.DOCX)
                .to(os)
                .as(DefaultDocumentFormatRegistry.PDF)
                .execute();
    }
}
