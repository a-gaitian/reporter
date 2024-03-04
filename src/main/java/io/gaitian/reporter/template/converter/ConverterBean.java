package io.gaitian.reporter.template.converter;

import io.gaitian.reporter.template.converter.annotation.Converts;
import io.gaitian.reporter.template.model.file.Format;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>Интерфейс для преобразователя типа файлов</p>
 * <p>Также необходимы аннотации {@link Converts}</p>
 */
public interface ConverterBean {
    void convert(Format from, InputStream is, Format to, OutputStream os);
}
