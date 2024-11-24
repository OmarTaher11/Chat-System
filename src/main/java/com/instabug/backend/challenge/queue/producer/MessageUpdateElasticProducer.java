package com.instabug.backend.challenge.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageUpdateElasticProducer {
    private static final String MESSAGE_EXCHANGE = "message-exchange";
    private static final String MESSAGE_ELASTIC_UPDATE_ROUTING_KEY = "message.elastic.update.routing.key";

    private final RabbitTemplate rabbitTemplate;

    public MessageUpdateElasticProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageIndexTask(Object messageTask) {
        rabbitTemplate.convertAndSend(MESSAGE_EXCHANGE, MESSAGE_ELASTIC_UPDATE_ROUTING_KEY, messageTask);
    }
}
