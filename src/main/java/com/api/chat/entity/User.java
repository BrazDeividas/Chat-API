package com.api.chat.entity;

import com.api.chat.entity.audit.DateAudit;
import com.api.chat.entity.role.RoleName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class User extends DateAudit {
    @Id
    private Integer id;
    private String username;
    private String password;
    private RoleName role;

    public User(String username, String password, RoleName role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.setCreatedAt(LocalDateTime.now());
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = RoleName.ROLE_USER;
        this.setCreatedAt(LocalDateTime.now());
    }

    public User(Integer id, String username, String password, RoleName role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.setCreatedAt(createdAt);
    }
}
