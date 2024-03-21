package com.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

//    @Column(nullable = false)
//    private Date createdDate;
//
//    @PrePersist
//    protected void onCreate() {
//        createdDate = new Date(); // Đặt giá trị ngày đăng khi thêm mới
//    }

    @Column(nullable = false)
    private String createdDate; // Sử dụng kiểu String cho ngày đăng
    @PrePersist
    protected void onCreate() {
        // Định dạng ngày giờ thành chuỗi ngày tháng giờ phút giây
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        createdDate = formatter.format(new Date());
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;




}