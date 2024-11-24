package com.instabug.backend.challenge.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.application.entity.Application;
import com.instabug.backend.challenge.chat.dto.ChatResponse;
import com.instabug.backend.challenge.chat.entity.Chat;
import com.instabug.backend.challenge.application.repository.ApplicationRepository;
import com.instabug.backend.challenge.chat.repository.ChatRepository;
import com.instabug.backend.challenge.queue.producer.ChatProducer;
import com.instabug.backend.challenge.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class ChatService {

    private final ApplicationRepository applicationRepository;
    private final ChatRepository chatRepository;
    private final RedisService redisService;
    private final ChatProducer chatProducer;
    private final ObjectMapper objectMapper;


    @Autowired
    public ChatService(ApplicationRepository applicationRepository, ChatRepository chatRepository, RedisService redisService, ChatProducer chatProducer, ObjectMapper objectMapper) {
        this.applicationRepository = applicationRepository;
        this.chatRepository = chatRepository;
        this.redisService = redisService;
        this.chatProducer = chatProducer;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ChatResponse createChat(String applicationToken) throws JsonProcessingException {
        Application application = applicationRepository.findByToken(applicationToken);
        if (application == null) {
            throw new NoSuchElementException("Application not found");
        }
        long chatNumber = getNextChatNumber(application.getToken());

        Chat chat = new Chat();
        chat.setApplication(application);
        chat.setNumber(chatNumber);
        chat.setApplicationToken(applicationToken);
        this.chatProducer.sendChatCreationTask(this.objectMapper.writeValueAsString(chat));
        return ChatResponse.builder().number(chat.getNumber()).messagesCount(chat.getMessagesCount()).build();
    }

    public Page<ChatResponse> getChatsForApplication(String applicationToken, int page, int size) {
        Application application = applicationRepository.findByToken(applicationToken);
        if (application == null) {
            throw new NoSuchElementException("Application not found");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Chat> chats = chatRepository.findByApplication(application, pageable);
        if (chats == null) {
            throw new NoSuchElementException("Chats not found");
        }
        return chats.map(chat -> ChatResponse.builder().number(chat.getNumber()).messagesCount(chat.getMessagesCount()).build());
    }

    private long getNextChatNumber(String applicationToken) {
        return this.redisService.getNextNumber(applicationToken);
    }
}
