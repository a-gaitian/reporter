package io.gaitian.reporter.template.converter.impl;

import io.gaitian.reporter.template.converter.ConverterBean;
import io.gaitian.reporter.template.converter.annotation.Converts;
import io.gaitian.reporter.template.model.file.Format;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Converts(from = {
        Format.DOC, Format.DOCX, Format.ODT
}, to = {
        Format.PDF
})
public class JodConverter implements ConverterBean {
    private final DocumentConverter converter;

    @SneakyThrows
    public void convert(Format from, InputStream is, Format to, OutputStream os) {

        log.trace("Converting from {} to {}", from, to);

        converter.convert(is)
                .as(docFormat(from))
                .to(os)
                .as(docFormat(to))
                .execute();
    }

    private DocumentFormat docFormat(Format format) {
        return switch (format) {
            case DOC -> DefaultDocumentFormatRegistry.DOC;
            case DOCX -> DefaultDocumentFormatRegistry.DOCX;
            case ODT -> DefaultDocumentFormatRegistry.ODT;
            case PDF -> DefaultDocumentFormatRegistry.PDF;
            default -> Optional.ofNullable(
                    DefaultDocumentFormatRegistry.getFormatByExtension(format.name())
            ).orElseThrow(() -> new IllegalArgumentException("Unknown format: " + format));
        };
    }
}
