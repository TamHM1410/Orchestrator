package application.config.exception;


import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private int code;
    private String message;
}
