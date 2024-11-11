package ru.cloudfilestorage.cloudfilestorage.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with user information")
public class UserResponse {

    private Long userId;

    private String email;

}
