package io.gaitian.reporter.api;

import io.gaitian.reporter.template.model.file.Format;
import org.springframework.http.MediaType;

public final class MediaTypeUtils {

    public static final String MEDIA_TYPE_DOCX_VALUE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static Format getFormat(MediaType mediaType) {
        return switch (mediaType.getType()) {
            case MediaType.APPLICATION_PDF_VALUE -> Format.PDF;
            case MEDIA_TYPE_DOCX_VALUE -> Format.DOCX;
            default -> throw new IllegalArgumentException("Unsupported media type: " + mediaType);
        };
    }

    public static MediaType getMediaType(Format format) {
        return switch (format) {
            case PDF -> MediaType.APPLICATION_PDF;
            case DOCX -> MediaType.parseMediaType(MEDIA_TYPE_DOCX_VALUE);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
