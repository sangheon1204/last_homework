package com.sparta.homework.entity;

import com.sparta.homework.dto.PostRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int likescount=0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostLikeCount> postLikeCounts = new ArrayList<>();




    public Post(PostRequestDto requestDto, String username, User user) {
        this.title = requestDto.getTitle();
        this.username = username;
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {//내용 수정
        if(requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if(requestDto.getContent() != null) {
            this.content = requestDto.getContent();
        }
    }

    public void addLike() {
        this.likescount +=1;
    }
    public void cancelLike() {
        this.likescount -= 1;
    }
}
