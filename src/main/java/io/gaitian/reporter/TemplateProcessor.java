package io.gaitian.reporter;

import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TemplateProcessor {

    @SneakyThrows
    public void process(InputStream template, Map<String, Object> context, FieldsMetadata metadata, OutputStream os) {
        var report = XDocReportRegistry.getRegistry()
                .loadReport(template, TemplateEngineKind.Velocity);

        report.process(new HashMap<>(context), os);
    }
}
