package com.instabug.backend.challenge.chat.entity;
import com.instabug.backend.challenge.application.entity.Application;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "chats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"application_id", "number"})
        },
        indexes = {
                @Index(name = "idx_application_token_chat_number", columnList = "application_token, number")
        }
)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "application_id", nullable = false, updatable = false)
    private Application application;

    @Column(name = "application_token", nullable = false, updatable = false)
    private String applicationToken;

    @Column(name = "messages_count", nullable = false)
    private Long messagesCount = 0L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
