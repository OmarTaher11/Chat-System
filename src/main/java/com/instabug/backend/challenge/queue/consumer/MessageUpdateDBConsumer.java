package com.instabug.backend.challenge.queue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.message.dto.UpdateMessageDto;
import com.instabug.backend.challenge.message.entity.Message;
import com.instabug.backend.challenge.message.repository.MessageRepository;
import com.instabug.backend.challenge.redis.service.RedisService;
import com.instabug.backend.challenge.utils.enums.RedisTokens;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageUpdateDBConsumer {
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;


    public MessageUpdateDBConsumer(ObjectMapper objectMapper, MessageRepository messageRepository) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = "message-mysql-update-queue")
    public void handleMessageSaveTask(String messageTask) {
        try {
            UpdateMessageDto updateMessageDto = this.objectMapper.readValue(messageTask, UpdateMessageDto.class);
            Message message = this.messageRepository.findByApplicationTokenAndChatNumberAndMessageNumber(updateMessageDto.getApplicationToken(), updateMessageDto.getChatNumber(), updateMessageDto.getMessageNumber());
            message.setBody(updateMessageDto.getUpdatedMessage().getBody());
            Message savedMessage = messageRepository.save(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message: " + e.getMessage());
        }
    }
}
