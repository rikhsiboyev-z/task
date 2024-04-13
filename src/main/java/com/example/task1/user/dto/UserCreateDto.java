package com.example.task1.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {

    private String username;
    private String password;
    @Email
    private String email;
    private String role;
    private Integer groupId;
}
