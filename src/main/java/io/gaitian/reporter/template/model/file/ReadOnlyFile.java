package io.gaitian.reporter.template.model.file;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

@Value
@RequiredArgsConstructor
public class ReadOnlyFile implements File {

    InputStream inputStream;
    Format format;

    public ReadOnlyFile(File file) {
        inputStream = file.getInputStream();
        format = file.getFormat();
    }

    @SneakyThrows
    public ReadOnlyFile(MultipartFile multipartFile, Format format) {
        inputStream = multipartFile.getInputStream();
        this.format = format;
    }

    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }
}
