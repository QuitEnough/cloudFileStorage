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

    public BaseException(String message) {
        super(message);
    }
}
