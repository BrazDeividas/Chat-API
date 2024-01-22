package com.api.chat.entity;

import java.time.LocalDateTime;

import com.api.chat.entity.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name="messages")
public class Message extends DateAudit {
    @Id
    private Integer id;
    private String content;
    private Integer senderId;
    private String sender;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.setCreatedAt(LocalDateTime.now());
    }

    public Message(String content, Integer senderId) {
        this.content = content;
        this.senderId = senderId;
        this.setCreatedAt(LocalDateTime.now());
    }

    public Message(Integer id, String content, Integer senderId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.setCreatedAt(createdAt);
    }
}
