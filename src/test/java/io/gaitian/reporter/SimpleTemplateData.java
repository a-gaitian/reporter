package io.gaitian.reporter;

import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public final class SimpleTemplateData {

    static final Random random = new Random(System.currentTimeMillis());

    public static final Map<String, Object> DATA = Map.of(
            "currentDate", OffsetDateTime.now().format(
                    DateTimeFormatter.ofPattern("d MMMM yyyy г. HH:mm:ss", Locale.of("ru"))),
            "report", Map.of(
                    "randomNumber", random.nextInt(),
                    "randomDecimal", random.nextDouble()
            ),
            "strings", List.of("Строка 1", "Строка два", "Строка номер 3", "Четвёртая строка"),
            "workers", List.of(
                    new Worker("Иван", "Иванов", 30),
                    new Worker("Петр", "Петров", 31),
                    new Worker("Сергей", "Сергеев", 32)
            ),
            "banner", new ReadOnlyFile(SimpleTemplateData.class.getClassLoader().getResourceAsStream("banner.png"), Format.IMAGE)
    );
}
