package io.gaitian.reporter.api;

import io.gaitian.reporter.api.dto.ReportRequest;
import io.gaitian.reporter.api.dto.ReportRequestMapper;
import io.gaitian.reporter.template.model.file.Format;
import io.gaitian.reporter.template.model.file.ReadOnlyFile;
import io.gaitian.reporter.template.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportController {

    private final TemplateService templateService;
    private final ReportRequestMapper mapper;

    @PostMapping(
        value = "report/{from}/{to}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody ResponseEntity<InputStreamResource> report(
            @PathVariable String from,
            @PathVariable String to,
            @Valid @ModelAttribute ReportRequest request
    ) {
        var fromFormat = Format.valueOf(from.toUpperCase());
        var toFormat = Format.valueOf(to.toUpperCase());

        var data = mapper.map(request);

        log.trace("data: {}", data);

        var result = templateService.compose(
                new ReadOnlyFile(request.getTemplate(), fromFormat),
                data, toFormat
        );

        return ResponseEntity.ok()
                .contentType(MediaTypeUtils.getMediaType(result.getFormat()))
                .body(new InputStreamResource(result.getInputStream()));
    }
}
