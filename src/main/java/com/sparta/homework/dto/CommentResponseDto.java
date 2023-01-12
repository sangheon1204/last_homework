package com.sparta.homework.dto;

import com.sparta.homework.entity.Comment;
import com.sparta.homework.entity.CommentLikeCount;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private int likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = comment.getPost().getUsername();
        this.likes =comment.getLikesCount();
    }
}
