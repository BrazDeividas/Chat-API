package com.api.chat.service.impl;

import com.api.chat.entity.Message;
import com.api.chat.entity.User;
import com.api.chat.payload.MessageRequest;
import com.api.chat.payload.MessageResponse;
import com.api.chat.repository.MessageRepository;
import com.api.chat.repository.UserRepository;
import com.api.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<MessageResponse> getMessages() {
        List<Message> messages = messageRepository.findAll();
        List<User> users = userRepository.findAll();

        messages.forEach(message -> {
            users.stream()
                    .filter(user -> user.getId().equals(message.getSenderId()))
                    .findFirst()
                    .ifPresent(user -> message.setSender(user.getUsername()));
        });

        return messages.stream()
                .map(m ->
                        new MessageResponse(m.getId(),
                                m.getSender() == null ? "Anonymous" : m.getSender(),
                                m.getContent(),
                                m.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessagesByUsername(String username) {
        User user = userRepository.findByUsername(username);
        var messages = messageRepository.findAll();
        return messages.stream()
                .filter(m ->
                        m.getSenderId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Message postMessage(MessageRequest message) {
        User user = userRepository.findByUsername(message.getUsername());
        Message newMessage = new Message(message.getContent(), user.getId());
        messageRepository.add(newMessage);
        return newMessage;

    }
}
