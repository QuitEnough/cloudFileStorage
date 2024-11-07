package ru.cloudfilestorage.cloudfilestorage.exception;

/**
 * Класс для обработки ошибок, связанных с сохранением, удалением и скачиваниес файлов.
 */
public class FileActionException extends RuntimeException {

    public FileActionException() {
    }

    public FileActionException(String message) {
        super(message);
    }

    public FileActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileActionException(Throwable cause) {
        super(cause);
    }

    public FileActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
