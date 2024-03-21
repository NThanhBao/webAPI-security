package com.security.service.serviceImpl;

import com.security.model.DTO.UserInfoDTO;
import com.security.model.entity.Role;
import com.security.model.entity.UserInfo;
import com.security.repository.RoleRepo;
import com.security.repository.UserInfoRepository;
import com.security.config.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final RoleRepo roleRepository;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserInfoService(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfoDTO userInfoDTO) {
        // Kiểm tra xem username đã tồn tại chưa
        if (repository.existsByUsername(userInfoDTO.getUsername())) {
            return "Username already exists";
        }

        Role userRole = roleRepository.findByRoleName(Role.RoleType.ROLE_USER);
        if (userRole == null) {
            throw new RuntimeException("khong the tao tai khoan vi 'Role User' chua duoc khoi tao. ");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoDTO.getUsername());
        userInfo.setPassword(encoder.encode(userInfoDTO.getPassword()));
        userInfo.setEmail(userInfoDTO.getEmail());
        userInfo.setPhone(userInfoDTO.getPhone());
        userInfo.setAdress(userInfoDTO.getAddress());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setFirstName(userInfoDTO.getFirstName());
        userInfo.setLastName(userInfoDTO.getLastName());
        userInfo.setBirthDay(userInfoDTO.getBirthDay());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setJob(userInfoDTO.getJob());


//        userInfo.setRoles(userInfoDTO.getRoles());
        userInfo.setRole(userRole);
        repository.save(userInfo);
        return "User Added Successfully with: " + userInfo.getUsername();
    }



}