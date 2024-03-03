package io.gaitian.reporter.template.converter;

import java.io.InputStream;
import java.io.OutputStream;

@FunctionalInterface
public interface Converter{
    void convert(InputStream is, OutputStream os);
}
