package com.instabug.backend.challenge.message.repository;

import com.instabug.backend.challenge.chat.entity.Chat;
import com.instabug.backend.challenge.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageJpaRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {


    Page<Message> findByChat(Chat chat, Pageable pageable);

    @Query("SELECT m FROM Message m " +
            "JOIN m.chat c " +
            "WHERE c.applicationToken = :applicationToken " +
            "AND c.number = :chatNumber " +
            "AND m.messageNumber = :messageNumber")
    Message findByApplicationTokenAndChatNumberAndMessageNumber(String applicationToken, Long chatNumber, Long messageNumber);
}
