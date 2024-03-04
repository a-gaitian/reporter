package io.gaitian.reporter.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gaitian.reporter.template.model.file.File;
import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReportRequestMapper {

    private final ObjectMapper mapper;

    /**
     * Преобразование JSON-содержимого запроса в контекст
     * с заменой ссылок на картинки ({@link ReportRequest#IMAGE_PREFIX}) на {@link File}
     * из массива {@link ReportRequest#images}
     */
    public Map<String, Object> map(ReportRequest request) {
        Map<String, Object> source;
        try {
            //noinspection unchecked
            source = mapper.readValue(request.getData(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("ReportRequest data parse failed", e);
        }

        return mapRecursive(source, request.getImages());
    }

    private Map<String, Object> mapRecursive(Map<String, Object> source, MultipartFile[] images) {
        Map<String, Object> map = new HashMap<>(source);
        map.replaceAll((key, value) -> {

            if (value instanceof Map) {
                //noinspection unchecked
                return mapRecursive((Map<String, Object>) value, images);

            }

            if (value instanceof String strValue
                    && strValue.startsWith(ReportRequest.IMAGE_PREFIX)) {

                var imageName = ((String) value).substring(ReportRequest.IMAGE_PREFIX.length());

                return Arrays.stream(images)
                        .filter(image -> imageName.equals(image.getOriginalFilename()))
                        .findFirst()
                        .map(image -> new ReadOnlyFile(image, Format.IMAGE))
                        .orElseThrow(() -> new IllegalArgumentException("Image " + strValue + " needed, but not found"));
            }

            return value;
        });
        return map;
    }
}
