package com.api.chat.service;

import com.api.chat.entity.User;
import com.api.chat.payload.UserRequest;
import com.api.chat.payload.UserStatistics;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserStatistics getStatistics(String username);
    User addUser(UserRequest user);
    User getById(Integer id);
    User getByUsername(String username);
    void deleteByUsername(String username);
    UserDetails loadUserByUsername(String username);
    void saveUser(User user);
}
