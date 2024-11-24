package com.instabug.backend.challenge.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageElasticProducer {
    private static final String MESSAGE_EXCHANGE = "message-exchange";
    private static final String MESSAGE_ELASTIC_ROUTING_KEY = "message.elastic.routing.key";

    private final RabbitTemplate rabbitTemplate;

    public MessageElasticProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageIndexTask(Object messageTask) {
        rabbitTemplate.convertAndSend(MESSAGE_EXCHANGE, MESSAGE_ELASTIC_ROUTING_KEY, messageTask);
    }
}
