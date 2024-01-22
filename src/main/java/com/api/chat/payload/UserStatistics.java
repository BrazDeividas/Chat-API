package com.api.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserStatistics {
    private Integer id;
    private String username;
    private int messageCount;
    private LocalDateTime firstMessageTime;
    private LocalDateTime lastMessageTime;
    private double averageMessageLength;
    private String lastMessage;
}
