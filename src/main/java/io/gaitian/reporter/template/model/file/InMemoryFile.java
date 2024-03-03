package io.gaitian.reporter.template.model.file;

import lombok.SneakyThrows;
import lombok.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Value
public class InMemoryFile implements File {

    Format format;
    ByteArrayOutputStream outputStream;

    @SneakyThrows
    public InMemoryFile(Format format) {
        this.format = format;
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
