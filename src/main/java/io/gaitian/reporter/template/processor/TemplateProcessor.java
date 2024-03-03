package io.gaitian.reporter.template.processor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface TemplateProcessor {
    void process(InputStream template, OutputStream os, Map<String, Object> data);
}
