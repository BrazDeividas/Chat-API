package com.api.chat.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@JsonIgnoreProperties(
        value = { "createdAt" },
        allowGetters = true
)
public abstract class DateAudit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
