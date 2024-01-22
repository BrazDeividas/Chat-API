package com.api.chat.repository;

import com.api.chat.entity.Message;

import java.util.List;

public interface MessageRepository {
    Message add(Message entry);
    Message delete(Integer id);
    List<Message> findAll();
    Message findById(Integer id);
    Message update(Message entry);
}
