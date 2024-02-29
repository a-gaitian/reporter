package io.gaitian.reporter;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class Converter {

    @SneakyThrows
    public void docxToPdf(InputStream is, OutputStream os) {
        var doc = new XWPFDocument(is);
        var options = PdfOptions.create();
        PdfConverter.getInstance().convert(doc, os, options);
    }
}
