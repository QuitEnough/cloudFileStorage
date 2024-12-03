package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "Request for registration")
@Builder
public class UserCreateRequest {

    @Schema(description = "Email address", example = "DimaBilan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате DimaBilan@gmail.com",
            regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Schema(description = "Password", example = "S1Pa$$w0")
    @Size(max = 255, message = "Длина пароля не может превышать 8 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    @Pattern(message = "Пароль должен быть по длине от 6 до 8 символов с 1 заглавной буквой", regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$")
    private String password;
}
