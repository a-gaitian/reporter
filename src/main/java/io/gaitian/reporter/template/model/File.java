package io.gaitian.reporter.template.model;

import java.io.InputStream;
import java.io.OutputStream;

public interface File<T extends Format> {

    InputStream inputStream();

    OutputStream outputStream();
}
