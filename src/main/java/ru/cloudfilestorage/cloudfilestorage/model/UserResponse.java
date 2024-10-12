package ru.cloudfilestorage.cloudfilestorage.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String userName;

}
