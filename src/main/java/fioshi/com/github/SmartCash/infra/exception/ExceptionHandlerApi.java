package fioshi.com.github.SmartCash.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerApi {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResposeDto> handleBussinessException(BusinessException ex, HttpServletRequest request) {
        var error = new ErrorResposeDto(HttpStatus.NOT_FOUND, ex, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
