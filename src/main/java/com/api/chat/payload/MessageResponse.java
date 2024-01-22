package com.api.chat.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageResponse {
    private Integer id;
    private String content;
    private String postedBy;
    private LocalDateTime postedAt;

    public MessageResponse(Integer id, String content, String postedBy, LocalDateTime postedAt) {
        this.id = id;
        this.content = content;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
    }
}
