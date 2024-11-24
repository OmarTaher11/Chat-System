package com.instabug.backend.challenge.queue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.message.document.MessageDocument;
import com.instabug.backend.challenge.message.dto.UpdateMessageDto;
import com.instabug.backend.challenge.message.elastic.MessageSearchRepository;
import com.instabug.backend.challenge.message.entity.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageUpdateElasticConsumer {
    private final ObjectMapper objectMapper;
    private final MessageSearchRepository messageSearchRepository;

    public MessageUpdateElasticConsumer(ObjectMapper objectMapper, MessageSearchRepository messageSearchRepository) {
        this.objectMapper = objectMapper;
        this.messageSearchRepository = messageSearchRepository;
    }

    @RabbitListener(queues = "message-elastic-update-queue")
    public void handleMessageIndexTask(String messageTask) {
        try {
            UpdateMessageDto updateMessageDto = this.objectMapper.readValue(messageTask, UpdateMessageDto.class);
            MessageDocument message = this.messageSearchRepository.findByApplicationTokenAndChatNumberAndMessageNumber(updateMessageDto.getApplicationToken(), updateMessageDto.getChatNumber(), updateMessageDto.getMessageNumber());
            message.setBody(updateMessageDto.getUpdatedMessage().getBody());
            this.messageSearchRepository.save(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message: " + e.getMessage());
        }
    }
}
