package com.sparta.homework.service;

import com.sparta.homework.dto.CommentRequestDto;
import com.sparta.homework.dto.CommentResponseDto;
import com.sparta.homework.dto.ResponseDto;
import com.sparta.homework.entity.*;
import com.sparta.homework.repository.CommentLikeCountRepository;
import com.sparta.homework.repository.CommentRepository;
import com.sparta.homework.repository.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentLikeCountRepository commentLikeCountRepository;


    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, post, user);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Comment comment;
        if (user.getRole() == UserRoleEnum.ADMIN) {
           comment = findPostAndCommentAdmin(id);
        } else {
            comment = findPostAndCommentUser(id,user);  //comment
        }
        comment.update(commentRequestDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    @Transactional
    public ResponseDto deleteComment(Long id, User user) {
        Comment comment;
        if (user.getRole() == UserRoleEnum.ADMIN) {
            comment = findPostAndCommentAdmin(id);
        } else {
            comment = findPostAndCommentUser(id,user);
        }
        commentRepository.delete(comment);
        ResponseDto responseDto = new ResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
        return responseDto;
    }
    @Transactional
    public ResponseDto addLike(Long id, User user) {
        //게시물을 찾기
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );
        ResponseDto responseDto;
        if(commentLikeCountRepository.findByUserAndComment(user,comment) != null) {
            comment.cancelLike();
            responseDto = new ResponseDto("좋아요를 취소했습니다.", HttpStatus.OK.value());
        }else{
            commentLikeCountRepository.save(new CommentLikeCount(comment, user));
            comment.addLike();
            responseDto = new ResponseDto("좋아요를 눌렀습니다.", HttpStatus.OK.value());
        }
        return responseDto;
    }

    public Comment findPostAndCommentAdmin(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = (Comment) commentRepository.findByIdAndPost(id, post).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        return comment;
    }

    public Comment findPostAndCommentUser(Long id, User user) {
        Post post = postRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = (Comment) commentRepository.findByIdAndPost(id, post).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!(post.getCommentList().get((id.intValue()-1)).equals(comment))) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}