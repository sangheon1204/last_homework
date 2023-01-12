package com.sparta.homework.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String msg;
    private int statusCode;

    public ResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}

