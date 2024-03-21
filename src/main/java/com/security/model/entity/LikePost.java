package com.security.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "post")
    private Posts post;

    @OneToOne
    @JoinColumn(name = "user")
    private UserInfo user;

}
