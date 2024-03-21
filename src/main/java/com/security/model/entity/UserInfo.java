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
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String adress;

    @Column
    private String phone;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date birthDay;

    @Column
    private String gender;

    @Column
    private String job;

//    @Column
//    private String roles;


    @Column(nullable = false)
    private String createdDate;
    @PrePersist
    protected void onCreate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        createdDate = formatter.format(new Date());
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;



}
