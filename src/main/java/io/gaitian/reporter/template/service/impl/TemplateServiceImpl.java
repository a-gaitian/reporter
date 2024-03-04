package io.gaitian.reporter.template.service.impl;

import io.gaitian.reporter.template.converter.ConverterProvider;
import io.gaitian.reporter.template.model.file.File;
import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.InMemoryFile;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;
import io.gaitian.reporter.template.processor.TemplateProcessor;
import io.gaitian.reporter.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateProcessor processor;
    private final ConverterProvider converterProvider;

    public File compose(File template, Map<String, Object> data, Format outputFormat) {

        // TODO Использовать другую реализацию для экономии памяти
        File processedTemplate = new InMemoryFile(template.getFormat());

        processor.process(template.getInputStream(), processedTemplate.getOutputStream(), data);

        File result = new InMemoryFile(outputFormat);

        converterProvider
                .getConverter(processedTemplate.getFormat(), outputFormat)
                .convert(processedTemplate.getInputStream(), result.getOutputStream());

        log.trace("Composing done");

        return new ReadOnlyFile(result);
    }
}
