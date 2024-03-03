package io.gaitian.reporter.api.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class TemplateReportRequest {
    MultipartFile template;
    MultipartFile[] images;
    String data;
}
