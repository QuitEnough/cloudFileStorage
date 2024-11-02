package ru.cloudfilestorage.cloudfilestorage.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
