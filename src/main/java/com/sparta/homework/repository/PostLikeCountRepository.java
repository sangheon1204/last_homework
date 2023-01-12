package com.sparta.homework.repository;

import com.sparta.homework.entity.Post;
import com.sparta.homework.entity.PostLikeCount;
import com.sparta.homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeCountRepository extends JpaRepository<PostLikeCount, Long> {
    PostLikeCount findByUserAndPost(User user, Post post);
}
