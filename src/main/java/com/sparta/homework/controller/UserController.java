package com.sparta.homework.controller;

import com.sparta.homework.dto.UserRequestDto;
import com.sparta.homework.dto.ResponseDto;
import com.sparta.homework.dto.LoginResponseDto;
import com.sparta.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }
}
