package ru.cloudfilestorage.cloudfilestorage.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Базовый класс для всех exception.
 * В controllerAdvice обрабатывается как BAD_REQUEST.
 * Выдвет ошибку 500.
 */
@Getter
@Setter
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
