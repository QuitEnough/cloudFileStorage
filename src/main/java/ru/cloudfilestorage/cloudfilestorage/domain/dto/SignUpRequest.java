package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Адрес электронной почты", example = "DimaBilan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате DimaBilan@gmail.com",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Schema(description = "Пароль", example = "Secret1Pa$$w0rd5")
    @Size(max = 255, message = "Длина пароля не может превышать 255 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(message = "Пароль недостаточно сильный", regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$")
    private String password;

}
