package application.config.exception;

import lombok.Data;

@Data
public class ErrorHandler {
    public static final int SUCCESS = 200;
    public static final int FAIL = 400;
    public static final int NOT_FOUND = 404;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int INTERNAL_SERVER_ERROR = 500;
    
    
    public static final String UNAUTHORIZED_MSG = "Unauthorized";
    public static final String FORBIDDEN_MSG = "Forbidden";
    public static final String NOT_FOUND_MSG = "Not Found";
    public static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error";


    //MSG
    
    
}
