package io.gaitian.reporter.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class ReportRequest {

    public static final String IMAGE_PREFIX = "image://";

    @NotNull MultipartFile template;

    @NotBlank String data;

    @NotNull MultipartFile[] images;
}
