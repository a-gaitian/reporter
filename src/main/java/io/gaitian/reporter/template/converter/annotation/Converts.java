package io.gaitian.reporter.template.converter.annotation;

import io.gaitian.reporter.template.model.file.Format;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConverterService.class)
public @interface Converts {

    Format[] from();

    Format[] to();
}
