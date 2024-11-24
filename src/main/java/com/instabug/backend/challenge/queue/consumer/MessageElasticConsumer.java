package com.instabug.backend.challenge.queue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.message.document.MessageDocument;
import com.instabug.backend.challenge.message.elastic.MessageSearchRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageElasticConsumer {
    private final ObjectMapper objectMapper;
    private final MessageSearchRepository messageSearchRepository;

    public MessageElasticConsumer(ObjectMapper objectMapper, MessageSearchRepository messageSearchRepository) {
        this.objectMapper = objectMapper;
        this.messageSearchRepository = messageSearchRepository;
    }

    @RabbitListener(queues = "message-elastic-queue")
    public void handleMessageIndexTask(String messageTask) {
        try {
            MessageDocument message = this.objectMapper.readValue(messageTask, MessageDocument.class);
            this.messageSearchRepository.save(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message: " + e.getMessage());
        }
    }
}
