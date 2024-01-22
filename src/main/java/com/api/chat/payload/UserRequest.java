package com.api.chat.payload;

import com.api.chat.entity.role.RoleName;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private RoleName role = RoleName.ROLE_USER;
}
