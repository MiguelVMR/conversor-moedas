package com.wefin.conversor_moedas.exception;

import com.wefin.conversor_moedas.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class GlobalExceptionHandler
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorRecorddResponse> handleLoginException(LoginException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorRecorddResponse(HttpStatus.UNAUTHORIZED.value(),ex.getMessage(),null));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorRecorddResponse> handleUserException(ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorRecorddResponse(HttpStatus.CONFLICT.value(),ex.getMessage(),null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecorddResponse> handleValidatedException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorRecorddResponse(HttpStatus.BAD_REQUEST.value(),"Erro na validação dos campos: ",errors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorRecorddResponse> handleBusinessException(BusinessException ex) {
        HttpStatus status = getStatusFromErrorType(ex.getErrorType());

        Map<String, String> details = new HashMap<>();
        details.put("errorType", ex.getErrorType().name());
        details.put("errorDescription", ex.getErrorType().getDescription());
        details.put("timestamp", LocalDateTime.now().toString());

        if (ex.getField() != null) {
            details.put("field", ex.getField());
        }

        return ResponseEntity
                .status(status)
                .body(new ErrorRecorddResponse(status.value(), ex.getMessage(), details));
    }

    private HttpStatus getStatusFromErrorType(ErrorType errorType) {
        return switch (errorType) {
            case ENTITY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_DATA, INSUFFICIENT_FUNDS, BUSINESS_RULE -> HttpStatus.BAD_REQUEST;
            case DATABASE_ERROR, UNEXPECTED_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
