package com.api.chat.service;

import com.api.chat.entity.Message;
import com.api.chat.payload.MessageRequest;
import com.api.chat.payload.MessageResponse;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getMessages();
    List<Message> getMessagesByUsername(String username);
    Message postMessage(MessageRequest message);
}
