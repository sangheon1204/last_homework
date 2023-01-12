package com.sparta.homework.repository;

import com.sparta.homework.entity.Comment;
import com.sparta.homework.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByPostOrderByModifiedAtDesc(Post post);

    Optional<Object> findByIdAndPost(Long id, Post post);
}
