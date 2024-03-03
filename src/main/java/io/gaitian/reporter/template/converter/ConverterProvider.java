package io.gaitian.reporter.template.converter;

import io.gaitian.reporter.template.converter.annotation.Converts;
import io.gaitian.reporter.template.model.file.Format;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ConverterProvider implements ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    private final MultiKeyMap<Format, ConverterBean> converterBeans = new MultiKeyMap<>();

    public Converter getConverter(Format from, Format to) {
        var converterBean = converterBeans.get(from, to);

        if (converterBean == null)
            throw new IllegalArgumentException("No converter found for " + from + " -> " + to);

        return (is, os) -> {
            converterBean.convert(from, is, to, os);
        };
    }

    @PostConstruct
    public void init() {

        var converterBeans = applicationContext.getBeansOfType(ConverterBean.class);

        for (var entry : converterBeans.entrySet()) {
            String beanName = entry.getKey();
            ConverterBean bean = entry.getValue();

            var convertsAnnotations = bean.getClass().getAnnotationsByType(Converts.class);

            if (convertsAnnotations.length == 0)
                throw new IllegalStateException("Converter " + beanName + " must have at least one @Converts annotation");

            for (var convertsAnnotation : convertsAnnotations) {
                for (var from : convertsAnnotation.from()) {
                    for (var to : convertsAnnotation.to()) {
                        if(this.converterBeans.containsKey(from, to)) {
                            log.warn(
                                    "Several converters for {} -> {} : {} (will be used) and {} (will be ignored)",
                                    from, to,
                                    this.converterBeans.get(from, to).getClass().getSimpleName(),
                                    bean.getClass().getSimpleName()
                            );
                        } else {
                            log.trace("Registering converter {} -> {} : {}", from, to, bean.getClass().getSimpleName());
                            this.converterBeans.put(from, to, bean);
                        }
                    }
                }
            }
        }
    }
}
