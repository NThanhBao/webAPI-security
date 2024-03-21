package com.security.model.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String gender;
    private String job;
}
