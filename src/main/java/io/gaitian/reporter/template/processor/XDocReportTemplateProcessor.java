package io.gaitian.reporter.template.processor;

import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.gaitian.reporter.template.model.file.File;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@Service
public class XDocReportTemplateProcessor implements TemplateProcessor {

    @SneakyThrows
    @Override
    public void process(InputStream template, OutputStream os, Map<String, Object> data) {

        log.trace("Processing template, using {}", data);

        var report = XDocReportRegistry.getRegistry()
                .loadReport(template, TemplateEngineKind.Velocity);

        var context = report.createContext();
        var fieldsMetadata = report.createFieldsMetadata();

        preProcessRecursive(data, context, fieldsMetadata, "");

        log.trace("context:{}", context);

        report.process(context, os);
    }

    @SneakyThrows
    private void preProcessRecursive(
            Map<String, Object> currentData,
            IContext context,
            FieldsMetadata metadata,
            String prefix
    ) {
        for (var entry : currentData.entrySet()) {
            String elementName = entry.getKey();
            String elementKey = prefix + elementName;
            Object element = entry.getValue();

            log.trace("Pre processing element: {} = {}", elementName, element);

            BiConsumer<String, Object> put = (String key, Object value) -> {
                log.trace("Put element: {} = {}", key, value);
                context.put(key, value);
            };

            if (element instanceof Map<?,?>) {
                //noinspection unchecked
                preProcessRecursive((Map<String, Object>) element, context, metadata, elementKey + ".");

            } else if (element instanceof Iterable<?>) {
                metadata.addFieldAsList(elementKey);
                put.accept(elementKey, element);

            } else if (element instanceof File) {
                metadata.addFieldAsImage(elementKey);
                put.accept(elementKey, ((File) element).getInputStream());

            } else if (ClassUtils.isPrimitiveOrWrapper(element.getClass()) || element instanceof String) {
                put.accept(elementKey, element);

            } else {
                metadata.load(elementKey, element.getClass());
                put.accept(elementKey, element);
            }
        }
    }
}
