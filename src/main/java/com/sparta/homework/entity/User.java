package com.sparta.homework.entity;

import com.sparta.homework.dto.ResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotNull(message = "username은 필수 값입니다.")
    @Size(min =4, max=10)
    @Pattern(regexp = "^[a-z]+[0-9]+$" )
    String username;


    @NotNull(message = "password는 필수 값입니다.")
    @Size(min =8, max=15)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]+$")
    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
