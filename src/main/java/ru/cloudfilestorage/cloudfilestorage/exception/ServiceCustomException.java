package ru.cloudfilestorage.cloudfilestorage.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCustomException extends RuntimeException {

    public ServiceCustomException(String message) {
        super(message);
    }
}
