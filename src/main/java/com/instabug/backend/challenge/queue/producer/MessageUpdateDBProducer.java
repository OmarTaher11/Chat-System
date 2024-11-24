package com.instabug.backend.challenge.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageUpdateDBProducer {

    private static final String MESSAGE_EXCHANGE = "message-exchange";
    private static final String MESSAGE_MYSQL_UPDATE_ROUTING_KEY = "message.mysql.update.routing.key";

    private final RabbitTemplate rabbitTemplate;

    public MessageUpdateDBProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageSaveTask(Object messageTask) {
        rabbitTemplate.convertAndSend(MESSAGE_EXCHANGE, MESSAGE_MYSQL_UPDATE_ROUTING_KEY, messageTask);
    }
}
