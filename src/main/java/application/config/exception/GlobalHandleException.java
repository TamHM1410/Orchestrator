package application.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalHandleException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(e.getCode());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(e.getCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleCommonException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
