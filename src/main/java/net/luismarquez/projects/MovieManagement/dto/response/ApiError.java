package net.luismarquez.projects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        @JsonProperty("http_code") int httpCode,
        String url,
        @JsonProperty("http_method") String httpMethod,
        String message,
        @JsonProperty("backend_message") String backendMessage,
        LocalDateTime timestamp,
        List<String> details
) {
}
