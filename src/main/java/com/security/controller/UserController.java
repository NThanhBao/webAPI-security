package com.security.controller;

import com.security.model.DTO.UserInfoDTO;
import com.security.model.entity.AuthRequest;
import com.security.config.JwtService;
import com.security.service.serviceImpl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserInfoDTO userInfoDTO) {
        return service.addUser(userInfoDTO);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public String userProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return role.equals("ROLE_ADMIN") ? "Welcome to Admin Profile" : "Welcome to User Profile";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // Nếu xác thực thành công, cập nhật bối cảnh bảo mật với thông tin xác thực
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về token hoặc thông tin người dùng đã đăng nhập thành công
            return "Token: " + jwtService.generateToken(authRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            return "Username not found"; // nếu username null
        } catch (Exception e) {
            return "Invalid username or password"; //nếu sai tk hoạc mk
        }



    }




}

