package ru.cloudfilestorage.cloudfilestorage.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceCustomException extends RuntimeException {

    public UserServiceCustomException(String message) {
        super(message);
    }
}
