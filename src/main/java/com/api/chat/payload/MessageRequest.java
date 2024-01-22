package com.api.chat.payload;

import lombok.Data;

@Data
public class MessageRequest {
    private String content;
    private String username;
}
