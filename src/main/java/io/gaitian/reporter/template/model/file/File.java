package io.gaitian.reporter.template.model.file;

import java.io.InputStream;
import java.io.OutputStream;

public interface File {

    InputStream getInputStream();

    OutputStream getOutputStream();

    Format getFormat();
}
