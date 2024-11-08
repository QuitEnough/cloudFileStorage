package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with user information")
public class UserResponse {

    @NotNull
    @Schema(description = "User id", example = "1")
    private Long userId;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    @Schema(description = "Email address", example = "DimaBilan@gmail.com")
    private String email;

}
