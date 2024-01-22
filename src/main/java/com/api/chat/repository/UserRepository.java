package com.api.chat.repository;

import com.api.chat.entity.User;

import java.util.List;

public interface UserRepository {
    User add(User entry);
    User delete(Integer id);
    List<User> findAll();
    User findById(Integer id);
    User findByUsername(String username);
    User update(User entry);
}
