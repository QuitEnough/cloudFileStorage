package ru.cloudfilestorage.cloudfilestorage.exception;

public class SaveFileException extends RuntimeException {

    public SaveFileException() {
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFileException(Throwable cause) {
        super(cause);
    }

    public SaveFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
