package com.api.chat.controller;

import com.api.chat.configuration.LoadDatabase;
import com.api.chat.entity.Message;
import com.api.chat.payload.MessageRequest;
import com.api.chat.payload.MessageResponse;
import com.api.chat.service.MessageService;
import com.api.chat.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<List<MessageResponse>> getAll() {
        var messages = messageService.getMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<Message> postMessage(@RequestBody MessageRequest message, @RequestHeader(name = "Authorization") String jwtToken) {
        try{
            var username = jwtUtil.getUserNameFromJwtToken(jwtToken.substring(7));
            message.setUsername(username);
            Message newMessage = messageService.postMessage(message);
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        }
        catch(UsernameNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
