package com.sparta.homework.dto;

import com.sparta.homework.entity.Post;
import com.sparta.homework.entity.PostLikeCount;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    //그 외의 경우
    public PostResponseDto(Post post, List<CommentResponseDto> commentResponseDtos) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        username = post.getUsername();
        likes =post.getLikescount();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
        commentList = commentResponseDtos;
    }
    //처음 생성할 때
    public PostResponseDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        username = post.getUsername();
        likes = post.getLikescount();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }
}

