package com.security.service.serviceImpl;

import com.security.model.entity.Role;
import com.security.model.entity.UserInfo;
import com.security.repository.RoleRepo;
import com.security.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DataInitializer {
    private final RoleRepo roleRepository;
    private final UserInfoRepository userDao;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public DataInitializer(RoleRepo roleRepository, UserInfoRepository userDao, BCryptPasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userDao = userDao;
        this.encoder = encoder;
    }
    @PostConstruct
    public void initializeData() {
        initializeRoles();
        createAdminUser();
    }
    private void initializeRoles() {
        if (roleRepository.findByRoleName(Role.RoleType.ROLE_ADMIN) == null) {
            Role adminRole = new Role();
            adminRole.setRoleName(Role.RoleType.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByRoleName(Role.RoleType.ROLE_USER) == null) {
            Role userRole = new Role();
            userRole.setRoleName(Role.RoleType.ROLE_USER);
            roleRepository.save(userRole);
        }
    }
    private void createAdminUser() {
        // Kiểm tra xem người dùng "admin" đã tồn tại hay chưa
        String adminUsername = "admin";
        if (userDao.findByUsername(adminUsername).isEmpty()) {
            // Lấy vai trò "admin" từ cơ sở dữ liệu
            Role adminRole = roleRepository.findByRoleName(Role.RoleType.ROLE_ADMIN);
            if (adminRole == null) {
                throw new RuntimeException("khong the tao tai khoan admin vi 'Role admin' chua duoc khoi tao. ");
            }
            // Tạo người dùng mới có vai trò "admin" và idRole là 1
            UserInfo adminUser = new UserInfo();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.setRole(adminRole);
            adminUser.setEmail("ntb09109@gmail.com");
            adminUser.setAdress("Quy Nhơn");
            adminUser.setPhone("0901975682");
            adminUser.setFirstName("Nguyễn");
            adminUser.setLastName("Thanh Bảo");

            Calendar calendar = Calendar.getInstance();
            calendar.set(2002, Calendar.OCTOBER, 25);
            Date birthDay = calendar.getTime();
            adminUser.setBirthDay(birthDay);

            adminUser.setGender("Nam");
            adminUser.setJob("intern");
            userDao.save(adminUser);
        }
    }
}
