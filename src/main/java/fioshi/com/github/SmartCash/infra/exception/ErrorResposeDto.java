package fioshi.com.github.SmartCash.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResposeDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public ErrorResposeDto(HttpStatus status, Exception ex, HttpServletRequest request) {
        this(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
