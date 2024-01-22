package com.api.chat.service.impl;

import com.api.chat.entity.Message;
import com.api.chat.entity.User;
import com.api.chat.entity.role.RoleName;
import com.api.chat.exception.UserExistsException;
import com.api.chat.payload.UserRequest;
import com.api.chat.payload.UserStatistics;
import com.api.chat.repository.UserRepository;
import com.api.chat.service.MessageService;
import com.api.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageService messageService;

    @Override
    public UserStatistics getStatistics(String username) {
        User user = userRepository.findByUsername(username);
        List<Message> messages = messageService.getMessagesByUsername(username);
        int messageCount = messages.size();

        return new UserStatistics(
                user.getId(),
                user.getUsername(),
                messageCount,
                messageCount == 0 ? null : messages.get(messageCount - 1).getCreatedAt(),
                messageCount == 0 ? null : messages.get(0).getCreatedAt(),
                messages.stream().mapToInt(m ->
                            m.getContent().length()
                        ).average().orElse(0.0),
                messages.get(0).getContent()
        );
    }

    @Override
    public User addUser(UserRequest user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        userRepository.add(newUser);
        return newUser;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        userRepository.delete(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public void saveUser(User user) {
        userRepository.update(user);
    }
}
