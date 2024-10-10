package ru.cloudfilestorage.cloudfilestorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.cloudfilestorage.cloudfilestorage.model.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserServiceCustomException.class})
    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceCustomException exception) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatus.NOT_FOUND);
    }

}
