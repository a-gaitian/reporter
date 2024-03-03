package io.gaitian.reporter.template;

import io.gaitian.reporter.template.model.file.File;
import io.gaitian.reporter.template.model.file.Format;

import java.util.Map;

public interface TemplateService {
    File compose(File template, Map<String, Object> data, Format outputFormat);
}
