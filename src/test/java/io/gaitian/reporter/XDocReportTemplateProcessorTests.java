package io.gaitian.reporter;

import fr.opensagres.xdocreport.core.XDocReportException;
import io.gaitian.reporter.base.SpringBootTestBase;
import io.gaitian.reporter.template.processor.XDocReportTemplateProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;

class XDocReportTemplateProcessorTests extends SpringBootTestBase {

    @Autowired
    private XDocReportTemplateProcessor processor;

    @Test
    void simpleTemplate() throws IOException, XDocReportException {
        try (var is = getClass().getClassLoader().getResourceAsStream("Simple Template.docx")) {
            try (var os = new FileOutputStream("src/test/resources/Simple Template Result.docx")) {
                assert is != null;

                processor.process(
                        is, os, SimpleTemplateData.DATA
                );
            }
        }
    }
}
