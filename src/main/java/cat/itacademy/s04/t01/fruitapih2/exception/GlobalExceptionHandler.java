package cat.itacademy.s04.t01.fruitapih2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
    handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()){
            fieldErrors.put(error.getField(),error.getDefaultMessage());
        }

        Map<String, Object> response= new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");
        response.put("details", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
