package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class AuthenticationRequest {

    @Schema(description = "Адрес электронной почты", example = "DimaBilan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате DimaBilan@gmail.com")
    private String email;

    @Schema(description = "Пароль")
    @Size(max = 255, message = "Длина пароля не может превышать 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}
