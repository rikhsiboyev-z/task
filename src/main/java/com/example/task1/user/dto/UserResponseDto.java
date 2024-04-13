package com.example.task1.user.dto;

import com.example.task1.group.dto.GroupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {

    private Integer id;
    private String username;
    private String email;
    private List<GroupResponseDto>  groups;
}
