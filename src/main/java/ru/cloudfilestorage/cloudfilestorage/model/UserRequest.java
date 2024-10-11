package ru.cloudfilestorage.cloudfilestorage.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserRequest {

    @NotNull
    @Length(min = 2)
    private String userName;

    @NotNull
    @Length(min = 2)
    private String password;

}
