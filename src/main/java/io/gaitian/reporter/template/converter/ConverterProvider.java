package io.gaitian.reporter.template.converter;

import io.gaitian.reporter.template.converter.annotation.Converts;
import io.gaitian.reporter.template.model.file.Format;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public final class ConverterProvider {

    private final Map<String, ConverterBean> converterBeans;

    @SuppressWarnings("NotNullFieldNotInitialized") // Initialized in @PostConstruct
    private MultiKeyMap<Format, ConverterBean> formatsToConverterBean;

    public Converter getConverter(Format from, Format to) {
        var converterBean = formatsToConverterBean.get(from, to);

        if (converterBean == null)
            throw new IllegalArgumentException("No converter found for " + from + " -> " + to);

        return (is, os) -> converterBean.convert(from, is, to, os);
    }

    @PostConstruct
    public void init() {

        formatsToConverterBean = converterBeans.entrySet().stream().flatMap(entry -> {
            String beanName = entry.getKey();
            ConverterBean bean = entry.getValue();

            var convertsAnnotations = bean.getClass().getAnnotationsByType(Converts.class);

            if (convertsAnnotations.length == 0)
                throw new IllegalStateException("Converter " + beanName + " must have at least one @Converts annotation");

            return Arrays.stream(convertsAnnotations)
                    .flatMap(annotation -> processBeanAnnotation(annotation, bean));

        }).collect(
                MultiKeyMap::new,
                (map, entry) -> {
                    var multiKey = entry.getKey();
                    var from = multiKey.getKey(0);
                    var to = multiKey.getKey(1);

                    var bean = chooseConverter(multiKey, map.get(multiKey), entry.getValue());
                    log.trace("Registering converter {} -> {} : {}", from, to, bean.getClass().getSimpleName());
                    map.put(multiKey, bean);
                },
                (map, other) -> other.forEach((multiKey, converterBean) -> {
                    //noinspection unchecked
                    map.put(multiKey, chooseConverter((MultiKey<Format>) multiKey, map.get(multiKey), converterBean));
                })
        );
    }

    private Stream<Map.Entry<MultiKey<Format>, ConverterBean>> processBeanAnnotation(
            Converts annotation,
            ConverterBean bean
    ) {
        return Arrays.stream(annotation.from())
                .flatMap(from ->
                        Arrays.stream(annotation.to())
                                .map(to -> new MultiKey<>(from, to))
                ).map(multiKey -> Map.entry(multiKey, bean));
    }

    private ConverterBean chooseConverter(
            MultiKey<Format> multiKey,
            @Nullable ConverterBean existing,
            ConverterBean other
    ) {
        if (existing != null) {
            log.warn(
                    "Several converters for {} -> {} : {} (will be used) and {} (will be ignored)",
                    multiKey.getKey(0), multiKey.getKey(1),
                    existing.getClass().getSimpleName(),
                    other.getClass().getSimpleName()
            );
            return existing;
        }
        return other;
    }
}
