package application.config.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
}
