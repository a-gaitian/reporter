package io.gaitian.reporter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gaitian.reporter.api.dto.TemplateReportRequest;
import io.gaitian.reporter.template.TemplateService;
import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
@RequiredArgsConstructor
@RestController
public class TemplateReportController {

    public static final String IMAGE_PREFIX = "image://";

    private final TemplateService templateService;
    private final ObjectMapper mapper;

    @PostMapping(
        value = "report/{from}/{to}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody ResponseEntity<InputStreamResource> report(
            @PathVariable String from,
            @PathVariable String to,
            @ModelAttribute TemplateReportRequest request
    ) {
        var fromFormat = Format.valueOf(from.toUpperCase());
        var toFormat = Format.valueOf(to.toUpperCase());

        var data = processData(request);

        log.trace("data: {}", data);

        var result = templateService.compose(
                new ReadOnlyFile(request.getTemplate(), fromFormat),
                data, toFormat
        );

        return ResponseEntity.ok()
                .contentType(MediaTypeUtils.getMediaType(result.getFormat()))
                .body(new InputStreamResource(result.getInputStream()));
    }

    @SneakyThrows
    private Map<String, Object> processData(TemplateReportRequest request) {
        Map<String, Object> source = mapper.readValue(request.getData(), HashMap.class);
        var data = new HashMap<String, Object>();
        processDataRecursive(source, data, request.getImages());
        return data;
    }

    private void processDataRecursive(Map<String, Object> source, Map<String, Object> data, MultipartFile[] images) {
        for (var entry : source.entrySet()) {
            var elementName = entry.getKey();
            var element = entry.getValue();

            if (element instanceof Map) {
                var subData = new HashMap<String, Object>();
                processDataRecursive((Map<String, Object>) element, subData, images);
                data.put(elementName, subData);

            } else if (element instanceof String && ((String) element).startsWith(IMAGE_PREFIX)) {
                var str = (String) element;
                var imageName = str.substring(IMAGE_PREFIX.length());
                log.trace("image: {}", imageName);
                Arrays.stream(images)
                        .filter(image -> imageName.equals(image.getOriginalFilename()))
                        .findFirst()
                        .ifPresent(image ->
                                data.put(elementName, new ReadOnlyFile(image, Format.IMAGE))
                        );
            } else {
                data.put(elementName, element);
            }
        }
    }
}
