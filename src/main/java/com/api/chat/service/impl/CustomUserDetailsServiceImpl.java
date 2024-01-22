package com.api.chat.service.impl;

import com.api.chat.entity.User;
import com.api.chat.repository.UserRepository;
import com.api.chat.service.CustomUserDetailsService;
import com.api.chat.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return UserPrincipal.create(user);
        throw new UsernameNotFoundException("User with username " + username + " not found");
    }

    @Override
    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id);
        if (user != null)
            return UserPrincipal.create(user);
        throw new UsernameNotFoundException("User with id " + id + " not found");
    }
}
