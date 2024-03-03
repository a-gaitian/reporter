package io.gaitian.reporter.template.converter;

import io.gaitian.reporter.template.model.Format;

import java.io.InputStream;
import java.io.OutputStream;

public interface ConverterBean {
    void convert(Format from, InputStream is, Format to, OutputStream os);
}
