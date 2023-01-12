package com.sparta.homework.controller;

import com.sparta.homework.dto.*;
import com.sparta.homework.security.UserDetailsImpl;
import com.sparta.homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // 게시물 작성하기
    @PostMapping ("/posts")
    public PostResponseDto creatPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    @GetMapping ("/posts")
    public PostResponseDtoList getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPostsById(@PathVariable Long id) {
        return postService.getPostsById(id);
    }
    @PutMapping("/posts/{id}") //id값을 받아서 수정 -> 입력한 비밀번호 확인 후 수정
    public PostResponseDto updatePost(@PathVariable Long id,@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       return postService.update(id,requestDto,userDetails.getUser());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseDto deletePost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.delete(id,userDetails.getUser());
    }

    @PutMapping("/posts/likes/{id}")
    public ResponseDto addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addLike(id,userDetails.getUser());
    }
}
