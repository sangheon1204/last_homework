package com.sparta.homework.service;

import com.sparta.homework.dto.*;
import com.sparta.homework.entity.*;
import com.sparta.homework.repository.CommentRepository;
import com.sparta.homework.repository.PostLikeCountRepository;
import com.sparta.homework.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostLikeCountRepository postLikeCountRepository;
    private final CommentRepository commentRepository;

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
            Post post = new Post(requestDto, user.getUsername(), user);
            postRepository.save(post);
//            PostLikeCount postLikeCount = new PostLikeCount(post,user);
//            postLikeCountRepository.save(postLikeCount);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
    }

    //전체 목록 조회
    @Transactional
    public PostResponseDtoList getPosts() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post : postList) {
            List<CommentResponseDto> commentResponseDtos;
            List<Comment> commentList = commentRepository.findAllByPostOrderByModifiedAtDesc(post);
            commentResponseDtos = commentList.stream().map(CommentResponseDto ::new).collect(Collectors.toList());
            postResponseDtoList.add(new PostResponseDto(post,commentResponseDtos));
        }
        PostResponseDtoList postResponseDtoList1 = new PostResponseDtoList(postResponseDtoList);
        return postResponseDtoList1;
    }

    //게시글 id로 게시글 찾기
    @Transactional
    public PostResponseDto getPostsById(Long id) {
        //게시글 조회
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        //댓글 리스트를 담는 dto 생성
        List<CommentResponseDto> commentResponseDtos;
        //게시글로 댓글 리스트 조회
        List<Comment> commentList = commentRepository.findAllByPostOrderByModifiedAtDesc(post);
        // comment -> commentResponseDto로 전환
        commentResponseDtos = commentList.stream().map(CommentResponseDto :: new).collect(Collectors.toList());
        // 반환값에 게시글과 댓글을 담아서 반환
        PostResponseDto getResponseDto = new PostResponseDto(post,commentResponseDtos);
        return getResponseDto;
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, User user) {
        Post post;
        if(user.getRole() == UserRoleEnum.ADMIN) {
            post = postRepository.findById(id).orElseThrow(
                    ()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
            );
        }else {
            post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
            );
        }
        post.update(requestDto);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            commentResponseDtos.add(new CommentResponseDto(comment));
        }
        PostResponseDto getResponseDto = new PostResponseDto(post, commentResponseDtos);
        return getResponseDto;
    }

    @Transactional
    public ResponseDto delete(Long id,User user) {
        Post post;
        if(user.getRole() == UserRoleEnum.ADMIN) {
            post = postRepository.findById(id).orElseThrow(
                    ()-> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );
        }else {
            post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );
        }
        postRepository.delete(post);
        ResponseDto responseDto = new ResponseDto("게시글 삭제 성공",HttpStatus.OK.value());
        return responseDto;
    }

    //좋아요 갯수
    @Transactional
    public ResponseDto addLike(Long id, User user) {
        //게시물을 찾기
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
        //게시글의 좋아요 갯수
        //유저가 좋아요를 눌렀으면 좋아요 갯수 뺴고 없으면 좋아요 갯수 하나 추가!
        ResponseDto responseDto;
        if(postLikeCountRepository.findByUserAndPost(user,post) != null) {
            post.cancelLike();
            responseDto = new ResponseDto("좋아요를 취소했습니다.", HttpStatus.OK.value());
        }else {
            postLikeCountRepository.save(new PostLikeCount(post,user));
            post.addLike();
            responseDto = new ResponseDto("좋아요를 눌렀습니다.", HttpStatus.OK.value());
        }
        return responseDto;
    }
}
