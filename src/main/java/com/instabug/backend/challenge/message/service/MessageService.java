package com.instabug.backend.challenge.message.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.application.entity.Application;
import com.instabug.backend.challenge.message.document.MessageDocument;
import com.instabug.backend.challenge.message.dto.*;
import com.instabug.backend.challenge.chat.entity.Chat;
import com.instabug.backend.challenge.message.elastic.MessageSearchRepository;
import com.instabug.backend.challenge.message.entity.Message;
import com.instabug.backend.challenge.chat.repository.ChatRepository;
import com.instabug.backend.challenge.message.repository.MessageRepository;
//import com.instabug.backend.challenge.repository.elastic.MessageSearchRepository;  // For ElasticSearch
import com.instabug.backend.challenge.queue.producer.MessageDBProducer;
import com.instabug.backend.challenge.queue.producer.MessageElasticProducer;
import com.instabug.backend.challenge.queue.producer.MessageUpdateDBProducer;
import com.instabug.backend.challenge.queue.producer.MessageUpdateElasticProducer;
import com.instabug.backend.challenge.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final MessageSearchRepository messageSearchRepository;
    private final RedisService redisService;
    private final MessageDBProducer messageDBProducer;
    private final MessageElasticProducer messageElasticProducer;
    private final MessageUpdateDBProducer messageUpdateDBProducer;
    private final MessageUpdateElasticProducer messageUpdateElasticProducer;
    private final ObjectMapper objectMapper;


    @Autowired
    public MessageService(ChatRepository chatRepository, MessageRepository messageRepository, MessageSearchRepository messageSearchRepository, RedisService redisService, MessageDBProducer messageDBProducer, MessageElasticProducer messageElasticProducer, MessageUpdateDBProducer messageUpdateDBProducer, MessageUpdateElasticProducer messageUpdateElasticProducer, ObjectMapper objectMapper) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.messageSearchRepository = messageSearchRepository;
        this.redisService = redisService;
        this.messageDBProducer = messageDBProducer;
        this.messageElasticProducer = messageElasticProducer;
        this.messageUpdateDBProducer = messageUpdateDBProducer;
        this.messageUpdateElasticProducer = messageUpdateElasticProducer;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public MessageResponse createMessage(String applicationToken, Long chatNumber, CreateMessageRequest request) throws JsonProcessingException {
        System.out.println("Finding chat");
        Optional<Chat> optionalChat = chatRepository.findByApplicationTokenAndNumber(applicationToken, chatNumber);
        System.out.println(optionalChat);
        if (optionalChat.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }
        System.out.println("getting nest message number");

        long messageNumber = getNextMessageNumber(applicationToken, chatNumber);
        System.out.println(messageNumber);

        Message message = new Message();
        message.setChatNumber(optionalChat.get().getNumber());
        message.setMessageNumber(messageNumber);
        message.setBody(request.getBody());
        message.setChat(optionalChat.get());

        this.messageDBProducer.sendMessageSaveTask(this.objectMapper.writeValueAsString(message));

        MessageDocument messageDocument = new MessageDocument();
        messageDocument.setMessageNumber(message.getMessageNumber());
        messageDocument.setChatNumber(message.getChatNumber());
        messageDocument.setBody(message.getBody());
        messageDocument.setApplicationToken(applicationToken);
        this.messageElasticProducer.sendMessageIndexTask(this.objectMapper.writeValueAsString(messageDocument));


        return MessageResponse.builder().body(message.getBody()).number(message.getMessageNumber()).build();
    }

    public Page<MessageResponse> getMessagesForChat(String applicationToken, Long chatNumber, int page, int size) {
        Optional<Chat> optionalChat = chatRepository.findByApplicationTokenAndNumber(applicationToken, chatNumber);
        if (optionalChat.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = messageRepository.findByChat(optionalChat.get(), pageable);
        return messages.map(message -> MessageResponse.builder()
                .body(message.getBody())
                .number(message.getMessageNumber()
                )
                .build());
    }

    public List<MessageSearchResponse> searchMessages(String applicationToken, Long chatNumber, String query) {
        Optional<Chat> optionalChat = chatRepository.findByApplicationTokenAndNumber(applicationToken, chatNumber);
        if (optionalChat.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }
        List<MessageDocument> messageDocuments = messageSearchRepository.findByApplicationTokenAndChatNumberAndBodyContaining(applicationToken, chatNumber, query);

        return messageDocuments.stream().map(document -> MessageSearchResponse.builder()
                .body(document.getBody())
                .messageId(document.getMessageNumber())
                .build()
        ).toList();

    }

    public MessageResponse updateMessage(String applicationToken, Long chatNumber, Long messageNumber, UpdateMessageRequest updatedMessage) throws JsonProcessingException {
        UpdateMessageDto updateMessageDto = UpdateMessageDto.builder().applicationToken(applicationToken).chatNumber(chatNumber).messageNumber(messageNumber).updatedMessage(updatedMessage).build();
        this.messageUpdateDBProducer.sendMessageSaveTask(this.objectMapper.writeValueAsString(updateMessageDto));
        this.messageUpdateElasticProducer.sendMessageIndexTask(this.objectMapper.writeValueAsString(updateMessageDto));
        return MessageResponse.builder()
                .body(updatedMessage.getBody())
                .number(messageNumber
                )
                .build();
    }

    private long getNextMessageNumber(String applicationToken, Long chatNumber) {
        return this.redisService.getNextNumber(applicationToken, chatNumber.toString());
    }
}
