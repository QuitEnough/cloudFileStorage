package ru.cloudfilestorage.cloudfilestorage.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userName;
    private String userId;

}
