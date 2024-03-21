package com.security.model.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class LikeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "comment")
    private Comment comment;

    @OneToOne
    @JoinColumn(name = "user")
    private UserInfo user;

}
