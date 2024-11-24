package com.instabug.backend.challenge.chat.repository;

import com.instabug.backend.challenge.application.entity.Application;
import com.instabug.backend.challenge.chat.dto.ChatInfoDTO;
import com.instabug.backend.challenge.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Page<Chat> findByApplication(Application application , Pageable pageable);

    Optional<Chat> findByApplicationTokenAndNumber(String applicationToken, Long number);

    @Query("SELECT new com.instabug.backend.challenge.chat.dto.ChatInfoDTO(c.number, c.application.token) FROM Chat c")
    List<ChatInfoDTO> findAllChatNumbersAndApplicationTokens();

    @Query("UPDATE Chat c SET c.messagesCount = :messagesCount WHERE c.number = :number AND c.applicationToken = :applicationToken")
    @Transactional
    @Modifying
    int updateMessagesCountByNumberAndApplicationToken(Long number, String applicationToken, Long messagesCount);
}
