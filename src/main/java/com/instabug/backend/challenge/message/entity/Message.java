package com.instabug.backend.challenge.message.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.instabug.backend.challenge.chat.entity.Chat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_number", nullable = false)
    private Long chatNumber;

    @Column(nullable = false)
    private Long messageNumber;

    @Column(nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "chat_id", referencedColumnName = "id", updatable = false)
    })
    private Chat chat;
}

