package com.sparta.homework.dto;

import com.sparta.homework.entity.Comment;
import com.sparta.homework.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private boolean admin;
    private String adminToken;


}
