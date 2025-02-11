package in.bushansirgur.restapi.exceptions;

import in.bushansirgur.restapi.io.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorObject handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("Throwing the ResourceNotFoundException from GlobalExceptionHandler id {}",ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DATA_NOT_FOUND")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", errors);
        errorResponse.put("timestamp", new Date());
        errorResponse.put("errorCode", "VALIDATION_FAILED");
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ItemExistsException.class)
    public ErrorObject handleItemExistsException(ItemExistsException ex, WebRequest request) {
        log.error("Throwing the ItemExistsException from GlobalExceptionHandler id {}",ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DATA_EXISTS")
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorObject handleGeneralException(ResourceNotFoundException ex, WebRequest request) {
        log.error("Throwing the Exception from GlobalExceptionHandler id {}",ex.getMessage());
        return ErrorObject.builder()
                .errorCode("UNEXPECTED_ERROR")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

}
