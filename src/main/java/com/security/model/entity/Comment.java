package com.security.model.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String createdDate; // Sử dụng kiểu String cho ngày đăng
    @PrePersist
    protected void onCreate() {
        // Định dạng ngày giờ thành chuỗi ngày tháng giờ phút giây
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        createdDate = formatter.format(new Date());
    }

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    // constructors, getters, setters
}
