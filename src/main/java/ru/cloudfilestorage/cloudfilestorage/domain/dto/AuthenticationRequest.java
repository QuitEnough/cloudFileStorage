package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request for Authentication")
public class AuthenticationRequest {

    @Schema(description = "Email address", example = "DimaBilan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате DimaBilan@gmail.com")
    private String email;

    @Schema(description = "Password", example = "S1Pa$$w0")
    @Size(max = 255, message = "Длина пароля не может превышать 8 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}
