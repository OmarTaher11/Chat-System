package com.instabug.backend.challenge.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatProducer {

    private static final String CHAT_EXCHANGE = "chat-exchange";
    private static final String CHAT_ROUTING_KEY = "chat.routing.key";

    private final RabbitTemplate rabbitTemplate;

    public ChatProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendChatCreationTask(Object chatTask) {
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE, CHAT_ROUTING_KEY, chatTask);
    }
}
