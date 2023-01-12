package com.sparta.homework.dto;


import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String msg;
    private int statusCode;

    public LoginResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
