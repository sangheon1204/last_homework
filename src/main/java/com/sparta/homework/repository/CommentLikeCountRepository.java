package com.sparta.homework.repository;

import com.sparta.homework.entity.Comment;
import com.sparta.homework.entity.CommentLikeCount;
import com.sparta.homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeCountRepository extends JpaRepository<CommentLikeCount,Long> {
    CommentLikeCount findByUserAndComment(User user, Comment comment);
}
