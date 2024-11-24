package com.instabug.backend.challenge.queue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.message.entity.Message;
import com.instabug.backend.challenge.message.repository.MessageRepository;
import com.instabug.backend.challenge.redis.service.RedisService;
import com.instabug.backend.challenge.utils.enums.RedisTokens;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDBConsumer {
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;
    private final RedisService redisService;


    public MessageDBConsumer(ObjectMapper objectMapper, MessageRepository messageRepository, RedisService redisService) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
        this.redisService = redisService;
    }

    @RabbitListener(queues = "message-mysql-queue")
    public void handleMessageSaveTask(String messageTask) {
        try {
            Message message = this.objectMapper.readValue(messageTask, Message.class);
            Message savedMessage = this.messageRepository.save((message));
            this.redisService.getNextNumber(savedMessage.getChat().getApplicationToken(), savedMessage.getChat().getNumber().toString(), RedisTokens.COUNT.getTokenKey());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message: " + e.getMessage());
        }
    }
}
