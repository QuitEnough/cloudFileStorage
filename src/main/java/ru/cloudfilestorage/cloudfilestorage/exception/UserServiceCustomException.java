package ru.cloudfilestorage.cloudfilestorage.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceCustomException extends RuntimeException {

    private String errorCode;

    public UserServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
