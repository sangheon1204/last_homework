package com.sparta.homework.dto;

import com.sparta.homework.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDtoList {
    private List<PostResponseDto> postlist;
    public PostResponseDtoList(List<PostResponseDto> postlist) {
        this.postlist = postlist;
    }
}
