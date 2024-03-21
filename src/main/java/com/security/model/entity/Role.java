package com.security.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleType roleName;

    public enum RoleType {
        ROLE_ADMIN(1),
        ROLE_USER(2);

        private final int id;

        RoleType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    // Constructors, getters, and setters

    public void setId(Long id) {this.id = id;}

    public String getRoleName() {
        return roleName.name();
    }
    public void setRoleName(RoleType roleName) {
        this.roleName = roleName;
    }

}