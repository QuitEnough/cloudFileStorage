package ru.cloudfilestorage.cloudfilestorage.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.cloudfilestorage.cloudfilestorage.domain.dto.ErrorResponse;

@ControllerAdvice
@Slf4j
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PSQLException.class)
    public final ResponseEntity<ErrorResponse> handlePSQLException(PSQLException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(DeleteFileException.class)
    public final ResponseEntity<ErrorResponse> handleDeleteFileException(DeleteFileException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(SaveFileException.class)
    public final ResponseEntity<ErrorResponse> handleSaveFileException(SaveFileException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler({DownloadFileException.class})
    public ResponseEntity<ErrorResponse> handleDownloadFileException(DownloadFileException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatusCode()));
    }

}
