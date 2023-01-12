package com.sparta.homework.controller;

import com.sparta.homework.dto.CommentRequestDto;
import com.sparta.homework.dto.CommentResponseDto;
import com.sparta.homework.dto.ResponseDto;
import com.sparta.homework.security.UserDetailsImpl;
import com.sparta.homework.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id,commentRequestDto,userDetails.getUser());
    }
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id,commentRequestDto,userDetails.getUser());
    }
    @DeleteMapping("/comment/{id}")
    public ResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id,userDetails.getUser());
    }
    @PutMapping("/comment/likes/{id}")
    public ResponseDto addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addLike(id,userDetails.getUser());
    }
}
