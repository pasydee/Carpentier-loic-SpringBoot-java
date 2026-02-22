package SafetyNet.Alerts.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationErrors(MethodArgumentNotValidException exception){

        Map<String, String> fieldError = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldError.put(error.getField(),error.getDefaultMessage()));

        Map<String,Object> reponse = new HashMap<>();

        reponse.put("status", HttpStatus.BAD_REQUEST.value());
        reponse.put("message","Validation error");
        reponse.put("errors",fieldError);

        return ResponseEntity.badRequest().body(reponse);
    }
}
