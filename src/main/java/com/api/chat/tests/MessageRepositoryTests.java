package com.api.chat.tests;

import com.api.chat.entity.Message;
import com.api.chat.entity.User;
import com.api.chat.repository.MessageRepository;
import com.api.chat.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageRepositoryTests {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndRetrieveMessage() {
        User sender = new User("johnny12", "johnny123");
        userRepository.add(sender);

        Message message = new Message("Hello, world!", sender.getId());
        messageRepository.add(message);

        Message retrievedMessage = messageRepository.findById(message.getId());
        assertEquals(retrievedMessage, message);
    }
}