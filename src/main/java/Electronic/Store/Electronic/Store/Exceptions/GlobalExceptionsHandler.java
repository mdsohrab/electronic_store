package Electronic.Store.Electronic.Store.Exceptions;

import Electronic.Store.Electronic.Store.Dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    //handler resources not found exceptions
    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionsHandler(ResourceNotFoundExceptions ex) {

        logger.info("Exceptions Handler invoked");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    //MethodArgumentNotValidExceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {

        List<ObjectError> allError = ex.getBindingResult().getAllErrors();

        Map<String, Object> response = new HashMap<>();
        allError.stream().forEach(objectError -> {

            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}
