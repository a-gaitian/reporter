package io.gaitian.reporter.template.converter.annotation;

import io.gaitian.reporter.template.converter.ConverterBean;
import io.gaitian.reporter.template.model.file.Format;

import java.lang.annotation.*;

/**
 * <p>Указывает, что класс может преобразовать любой из типов файлов {@link #from} в любой из {@link #to}</p>
 * <p>Для разных преобразований использовать несколько аннотаций</p>
 * <p>Также необходима реализация {@link ConverterBean}</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConverterService.class)
public @interface Converts {

    /**
     * Исходные типы файлов
     */
    Format[] from();

    /**
     *
     */
    Format[] to();
}
